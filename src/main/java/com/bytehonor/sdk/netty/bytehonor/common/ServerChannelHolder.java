package com.bytehonor.sdk.netty.bytehonor.common;

import java.util.stream.Stream;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author lijianqiang
 *
 */
public class ServerChannelHolder {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static int size() {
        return CHANNELS.size();
    }

    public static Stream<Channel> parallelStream() {
        return CHANNELS.parallelStream();
    }
    
    public static Stream<Channel> stream() {
        return CHANNELS.stream();
    }

    public static void add(Channel value) {
        if (value == null) {
            return;
        }
        CHANNELS.add(value);
    }

    public static Channel get(ChannelId id) {
        if (id == null) {
            return null;
        }
        return CHANNELS.find(id);
    }

    public static void remove(Channel value) {
        if (value == null) {
            return;
        }
        CHANNELS.remove(value);
    }
}
