package com.bytehonor.sdk.netty.bytehonor.common.cache;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

public class WhoisCacheHolder {

    private static int CAPACITY = 4096;

    /**
     * channelId, whois
     */
    private static final ConcurrentHashMap<ChannelId, String> CACHE = new ConcurrentHashMap<ChannelId, String>(
            CAPACITY);

    public static Set<Entry<ChannelId, String>> entrySet() {
        return CACHE.entrySet();
    }

    public static int size() {
        return CACHE.size();
    }

    public static void put(ChannelId channelId, String whois) {
        if (whois == null) {
            return;
        }
        CACHE.put(channelId, whois);
    }

    public static String getWhois(ChannelId channelId) {
        if (channelId == null) {
            return null;
        }
        return CACHE.get(channelId);
    }

    public static ChannelId getId(String whois) {
        if (whois == null) {
            return null;
        }
        ChannelId channelId = null;
        for (Entry<ChannelId, String> item : CACHE.entrySet()) {
            if (whois.equals(item.getValue())) {
                channelId = item.getKey();
                break;
            }
        }
        return channelId;
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }
        CACHE.remove(channelId);
    }
}
