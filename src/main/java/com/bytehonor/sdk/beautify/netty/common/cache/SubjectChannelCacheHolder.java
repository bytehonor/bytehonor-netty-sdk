package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
public class SubjectChannelCacheHolder {

    private static int CAPACITY = 1024;

    /**
     * subject channel
     */
    private static final ConcurrentHashMap<String, ChannelIdCacheHolder> MAP = new ConcurrentHashMap<String, ChannelIdCacheHolder>(
            CAPACITY);

    public static int size() {
        return MAP.size();
    }

    public static boolean exists(String subject, ChannelId channelId) {
        if (isEmpty(subject) || channelId == null) {
            return false;
        }
        ChannelIdCacheHolder holder = MAP.get(subject);
        if (holder == null) {
            return false;
        }
        return holder.contains(channelId);
    }

    public static synchronized void put(String subject, ChannelId channelId) {
        if (exists(subject, channelId)) {
            return;
        }

        ChannelIdCacheHolder holder = MAP.get(subject);
        if (holder == null) {
            holder = new ChannelIdCacheHolder();
            MAP.put(subject, holder);
        }
        holder.add(channelId);
    }

    public static Set<ChannelId> list(String subject) {
        if (isEmpty(subject)) {
            return new HashSet<ChannelId>();
        }
        ChannelIdCacheHolder holder = MAP.get(subject);
        if (holder == null) {
            return new HashSet<ChannelId>();
        }
        return holder.values();
    }

    public static void remove(String subject, ChannelId channelId) {
        if (isEmpty(subject)) {
            return;
        }

        ChannelIdCacheHolder holder = MAP.get(subject);
        if (holder == null) {
            return;
        }
        holder.remove(channelId);
    }

    private static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static Set<Entry<String, ChannelIdCacheHolder>> entrySet() {
        return MAP.entrySet();
    }
}
