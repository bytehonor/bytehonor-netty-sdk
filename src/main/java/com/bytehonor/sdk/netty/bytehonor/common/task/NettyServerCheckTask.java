package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.WhoisClientCacheHolder;

import io.netty.channel.ChannelId;

public class NettyServerCheckTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void run() {
        LOG.info("subscribe size:{}, whois size:{}", SubscribeChannelHolder.size(), WhoisClientCacheHolder.size());
        Iterator<Entry<String, ChannelId>> its = SubscribeChannelHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, ChannelId> item = its.next();
            if (ServerChannelHolder.get(item.getValue()) == null) {
                LOG.warn("remove key:{}", item.getKey());
                SubscribeChannelHolder.remove(item.getKey());
            }
        }

        Iterator<Entry<String, ChannelId>> itc = WhoisClientCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, ChannelId> item = itc.next();
            LOG.warn("check whois:{}, channel:{}", item.getKey(), item.getValue().asLongText());
            if (ServerChannelHolder.get(item.getValue()) == null) {
                LOG.warn("remove whois:{}, channel:{}", item.getKey(), item.getValue().asLongText());
                WhoisClientCacheHolder.remove(item.getKey());
            }
        }

    }

}
