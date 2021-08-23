package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.AppidChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;

import io.netty.channel.ChannelId;

public class NettyServerCheckTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void run() {
        LOG.info("subscribe size:{}, appid size:{}", SubscribeChannelHolder.size(), AppidChannelCacheHolder.size());
        Iterator<Entry<String, ChannelId>> its = SubscribeChannelHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, ChannelId> item = its.next();
            if (ServerChannelHolder.get(item.getValue()) == null) {
                LOG.warn("remove key:{}", item.getKey());
                SubscribeChannelHolder.remove(item.getKey());
            }
        }

        Iterator<Entry<String, ChannelId>> itc = AppidChannelCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, ChannelId> item = itc.next();
            LOG.warn("check appid:{}, channel:{}", item.getKey(), item.getValue());
            if (ServerChannelHolder.get(item.getValue()) == null) {
                LOG.warn("remove appid:{}, channel:{}", item.getKey(), item.getValue());
                AppidChannelCacheHolder.remove(item.getKey());
            }
        }

    }

}
