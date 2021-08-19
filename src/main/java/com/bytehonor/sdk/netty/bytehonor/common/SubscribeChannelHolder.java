package com.bytehonor.sdk.netty.bytehonor.common;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

public class SubscribeChannelHolder {

    private static int CAPACITY = 10240;

    private static final ConcurrentHashMap<String, ChannelId> MAP = new ConcurrentHashMap<String, ChannelId>(CAPACITY);

    public static String makeKey(ChannelId id, String value) {
        return new StringBuilder().append(id.asLongText()).append(":").append(value).toString();
    }

    public static Set<Entry<String, ChannelId>> entrySet() {
        return MAP.entrySet();
    }

    public static int size() {
        return MAP.size();
    }

    public static boolean exists(String key) {
        if (isEmpty(key)) {
            return false;
        }
        return MAP.containsKey(key);
    }

    public static void put(String key, ChannelId channelId) {
        if (isEmpty(key)) {
            return;
        }
        MAP.put(key, channelId);
    }

    public static ChannelId get(String key) {
        if (isEmpty(key)) {
            return null;
        }
        return MAP.get(key);
    }

    public static void remove(String key) {
        if (isEmpty(key)) {
            return;
        }
        MAP.remove(key);
    }

    private static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }
}
