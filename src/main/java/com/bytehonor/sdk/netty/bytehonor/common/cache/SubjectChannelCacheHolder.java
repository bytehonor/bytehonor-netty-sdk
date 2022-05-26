package com.bytehonor.sdk.netty.bytehonor.common.cache;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

import io.netty.channel.ChannelId;

public class SubjectChannelCacheHolder {

    private static int CAPACITY = 1024;

    /**
     * subject channel
     */
    private static final ConcurrentHashMap<String, Set<ChannelId>> MAP = new ConcurrentHashMap<String, Set<ChannelId>>(
            CAPACITY);

    public static int size() {
        return MAP.size();
    }

    public static boolean exists(String subject, ChannelId channelId) {
        if (isEmpty(subject) || channelId == null) {
            return false;
        }
        Set<ChannelId> channels = MAP.get(subject);
        if (CollectionUtils.isEmpty(channels)) {
            return false;
        }
        return channels.contains(channelId);
    }

    public static void add(String subject, ChannelId channelId) {
        if (exists(subject, channelId)) {
            return;
        }
        Set<ChannelId> channels = MAP.get(subject);
        if (CollectionUtils.isEmpty(channels)) {
            channels = new HashSet<ChannelId>();
        }
        channels.add(channelId);
        MAP.put(subject, channels);
    }

    public static Set<ChannelId> get(String subject) {
        if (isEmpty(subject)) {
            return new HashSet<ChannelId>();
        }
        Set<ChannelId> channels = MAP.get(subject);
        if (CollectionUtils.isEmpty(channels)) {
            return new HashSet<ChannelId>();
        }
        return channels;
    }

    public static void remove(String subject, ChannelId channelId) {
        if (isEmpty(subject)) {
            return;
        }
        Set<ChannelId> channels = MAP.get(subject);
        if (CollectionUtils.isEmpty(channels)) {
            return;
        }
        channels.remove(channelId);
    }

    public static void remove(String subject) {
        if (isEmpty(subject)) {
            return;
        }
        Set<ChannelId> channels = MAP.get(subject);
        if (CollectionUtils.isEmpty(channels)) {
            return;
        }
        channels.clear();
        MAP.remove(subject);
    }

    private static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static Set<Entry<String, Set<ChannelId>>> entrySet() {
        return MAP.entrySet();
    }
}
