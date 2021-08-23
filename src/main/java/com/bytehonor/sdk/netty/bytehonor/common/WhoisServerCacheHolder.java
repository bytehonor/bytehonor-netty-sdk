package com.bytehonor.sdk.netty.bytehonor.common;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

public class WhoisServerCacheHolder {

    private static int CAPACITY = 1024;

    private static final ConcurrentHashMap<String, ChannelId> CACHE = new ConcurrentHashMap<String, ChannelId>(
            CAPACITY);

    public static Set<Entry<String, ChannelId>> entrySet() {
        return CACHE.entrySet();
    }

    public static int size() {
        return CACHE.size();
    }

    public static void put(String whois, ChannelId channelId) {
        if (whois == null) {
            return;
        }
        CACHE.put(whois, channelId);
    }

    public static ChannelId get(String whois) {
        if (whois == null) {
            return null;
        }
        return CACHE.get(whois);
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }
        CACHE.remove(whois);
    }
}
