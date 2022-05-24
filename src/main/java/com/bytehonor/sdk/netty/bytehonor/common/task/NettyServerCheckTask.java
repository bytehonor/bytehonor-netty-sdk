package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelWhoisCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubscribeCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannel;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannels;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServerContanier;

public class NettyServerCheckTask extends NettyTask {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void runInSafe() {
        LOG.info("channel size:{}, subscribe size:{}, whois size:{}/{}", ChannelCacheHolder.size(),
                SubscribeCacheHolder.size(), ChannelWhoisCacheHolder.whoisSize(),
                ChannelWhoisCacheHolder.channelSize());

        Iterator<Entry<String, NettyChannels>> its = SubscribeCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, NettyChannels> item = its.next();
            String subject = item.getKey();
            List<NettyChannel> channels = item.getValue().getList();
            LOG.info("subject:{}, channels size:{}", subject, channels.size());
            for (NettyChannel channel : channels) {
                if (ChannelCacheHolder.get(channel.getId()) == null) {
                    LOG.warn("remove subject:{}, channel:{}", subject, channel.getLongText());
                    SubscribeCacheHolder.remove(subject, channel.getId());
                }
            }
        }

        // 限幅
        NettyServerContanier.limit();
    }

}
