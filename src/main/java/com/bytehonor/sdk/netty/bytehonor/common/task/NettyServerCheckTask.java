package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubscribeCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.WhoisCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannel;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannels;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServerContanier;

import io.netty.channel.ChannelId;

public class NettyServerCheckTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void run() {
        LOG.info("channel size:{}, subscribe size:{}, whois size:{}", ChannelCacheHolder.size(),
                SubscribeCacheHolder.size(), WhoisCacheHolder.size());

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

        Iterator<Entry<ChannelId, String>> itc = WhoisCacheHolder.entrySet().iterator();
        while (itc.hasNext()) {
            Entry<ChannelId, String> item = itc.next();
            LOG.info("whois:{}, channel:{}", item.getValue(), item.getKey().asLongText());
            if (ChannelCacheHolder.get(item.getKey()) == null) {
                LOG.warn("remove whois:{}", item.getValue());
                WhoisCacheHolder.remove(item.getKey());
            }
        }

        // 限幅
        NettyServerContanier.limit();

        // 通知
        NettyServerContanier.onTotal(ChannelCacheHolder.size());
    }

}
