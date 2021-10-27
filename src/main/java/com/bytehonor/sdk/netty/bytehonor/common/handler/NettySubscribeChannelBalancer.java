package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;

import io.netty.channel.Channel;

public class NettySubscribeChannelBalancer {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeChannelBalancer.class);

    public static void process(final String name, final int limit) {
        List<Channel> channels = Collections.synchronizedList(new ArrayList<Channel>());
        ServerChannelHolder.parallelStream().forEach(channel -> {
            String key = SubscribeChannelHolder.makeKey(channel.id(), name);
            if (SubscribeChannelHolder.exists(key) == false) {
                return;
            }
            channels.add(channel);
        });
        int size = channels.size();
        if (size > limit) {
            LOG.info("name:{}, limit:{}, size:{}", name, limit, size);
            Channel first = channels.get(0); // 踢掉第一个
            first.close();
        }
    }
}
