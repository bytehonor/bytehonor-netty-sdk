package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;

import io.netty.channel.ChannelId;

public class ChannelCheckTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelCheckTask.class);

    @Override
    public void run() {
        Iterator<Entry<String, ChannelId>> it = SubscribeChannelHolder.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, ChannelId> item = it.next();
            if (ServerChannelHolder.get(item.getValue()) == null) {
                LOG.warn("remove key:{}", item.getKey());
                SubscribeChannelHolder.remove(item.getKey());
            }
        }
    }

}
