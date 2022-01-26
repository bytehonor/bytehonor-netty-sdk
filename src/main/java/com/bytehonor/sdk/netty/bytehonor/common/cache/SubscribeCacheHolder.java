package com.bytehonor.sdk.netty.bytehonor.common.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannel;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyChannels;

import io.netty.channel.ChannelId;

public class SubscribeCacheHolder {

    private static int CAPACITY = 1024;

    /**
     * subject channel
     */
    private static final ConcurrentHashMap<String, NettyChannels> MAP = new ConcurrentHashMap<String, NettyChannels>(
            CAPACITY);

    public static int size() {
        return MAP.size();
    }

    public static boolean exists(String subject, ChannelId id) {
        if (isEmpty(subject)) {
            return false;
        }
        NettyChannels channels = MAP.get(subject);
        if (channels == null) {
            return false;
        }
        return channels.exists(id.asLongText());
    }

    public static void add(String subject, ChannelId id) {
        if (isEmpty(subject)) {
            return;
        }
        if (exists(subject, id)) {
            return;
        }
        NettyChannels channels = MAP.get(subject);
        if (channels == null) {
            channels = new NettyChannels();
        }
        channels.add(NettyChannel.of(id));
        MAP.put(subject, channels);
    }

    public static List<ChannelId> get(String subject) {
        if (isEmpty(subject)) {
            return new ArrayList<ChannelId>();
        }
        NettyChannels channels = MAP.get(subject);
        if (channels == null) {
            return new ArrayList<ChannelId>();
        }
        List<ChannelId> list = new ArrayList<ChannelId>(channels.size() * 2);
        for (NettyChannel channel : channels.getList()) {
            list.add(channel.getId());
        }
        return list;
    }

    public static void remove(String subject, ChannelId id) {
        if (isEmpty(subject)) {
            return;
        }
        NettyChannels channels = MAP.get(subject);
        if (channels == null) {
            return;
        }
        channels.remove(id.asLongText());
    }

    public static void remove(String subject) {
        if (isEmpty(subject)) {
            return;
        }
        NettyChannels channels = MAP.get(subject);
        if (channels == null) {
            return;
        }
        channels.removeAll();
        MAP.remove(subject);
    }

    private static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static Set<Entry<String, NettyChannels>> entrySet() {
        return MAP.entrySet();
    }
}
