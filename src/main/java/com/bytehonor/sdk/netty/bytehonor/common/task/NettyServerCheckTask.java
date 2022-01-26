package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeSubjectCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.WhoisClientCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.limiter.NettySubscribeSubjectLimiter;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannel;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannels;

import io.netty.channel.ChannelId;

public class NettyServerCheckTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void run() {
        LOG.info("subscribe size:{}, whois size:{}", SubscribeSubjectCacheHolder.size(), WhoisClientCacheHolder.size());
        Iterator<Entry<String, NettyChannels>> its = SubscribeSubjectCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, NettyChannels> item = its.next();
            String subject = item.getKey();
            List<NettyChannel> channels = item.getValue().getList();
            LOG.info("subject:{}, channels size:{}", subject, channels.size());
            for (NettyChannel channel : channels) {
                if (ChannelCacheHolder.get(channel.getId()) == null) {
                    LOG.warn("remove subject:{}, channel:{}", subject, channel.getLongText());
                    SubscribeSubjectCacheHolder.remove(subject, channel.getId());
                }
            }
        }

        Iterator<Entry<String, ChannelId>> itc = WhoisClientCacheHolder.entrySet().iterator();
        while (itc.hasNext()) {
            Entry<String, ChannelId> item = itc.next();
            LOG.info("whois:{}, channel:{}", item.getKey(), item.getValue().asLongText());
            if (ChannelCacheHolder.get(item.getValue()) == null) {
                LOG.warn("remove whois:{}, channel:{}", item.getKey(), item.getValue().asLongText());
                WhoisClientCacheHolder.remove(item.getKey());
            }
        }

        // 限幅
        NettySubscribeSubjectLimiter.process();
    }

}
