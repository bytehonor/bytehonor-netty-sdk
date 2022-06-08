package com.bytehonor.sdk.beautify.netty.common.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author lijianqiang
 *
 */
public class ChannelCacheHolder {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static int size() {
        return CHANNELS.size();
    }

//    public static Stream<Channel> parallelStream() {
//        return CHANNELS.parallelStream();
//    }
//
//    public static Stream<Channel> stream() {
//        return CHANNELS.stream();
//    }

    public static void add(Channel channel) {
        if (channel == null) {
            return;
        }
        if (get(channel.id()) != null) {
            return;
        }
        CHANNELS.add(channel);
    }

    public static Channel get(ChannelId id) {
        if (id == null) {
            return null;
        }
        return CHANNELS.find(id);
    }

    public static void remove(Channel channel) {
        if (channel == null) {
            return;
        }
        CHANNELS.remove(channel);
    }
}
