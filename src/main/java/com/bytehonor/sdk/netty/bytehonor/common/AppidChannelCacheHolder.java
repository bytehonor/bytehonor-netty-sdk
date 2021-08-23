package com.bytehonor.sdk.netty.bytehonor.common;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

public class AppidChannelCacheHolder {

    private static int CAPACITY = 1024;

    private static final ConcurrentHashMap<String, ChannelId> CACHE = new ConcurrentHashMap<String, ChannelId>(
            CAPACITY);

    public static Set<Entry<String, ChannelId>> entrySet() {
        return CACHE.entrySet();
    }

    public static int size() {
        return CACHE.size();
    }

    public static void put(String appid, ChannelId channelId) {
        if (appid == null) {
            return;
        }
        CACHE.put(appid, channelId);
    }

    public static ChannelId get(String appid) {
        if (appid == null) {
            return null;
        }
        return CACHE.get(appid);
    }

    public static void remove(String appid) {
        if (appid == null) {
            return;
        }
        CACHE.remove(appid);
    }
}
