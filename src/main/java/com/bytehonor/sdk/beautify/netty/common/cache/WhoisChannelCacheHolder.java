package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.channel.ChannelId;

public class WhoisChannelCacheHolder {

    private static final int CAP = NettyConstants.CAPACITY;
    /**
     * channelId, whois
     */
    private static final ConcurrentHashMap<ChannelId, String> CHANNEL = new ConcurrentHashMap<ChannelId, String>(CAP);

    /**
     * whois channelId
     */
    private static final ConcurrentHashMap<String, ChannelId> WHOIS = new ConcurrentHashMap<String, ChannelId>(CAP);

    public static int channelSize() {
        return CHANNEL.size();
    }

    public static int whoisSize() {
        return WHOIS.size();
    }

    public static void put(String whois, ChannelId channelId) {
        Objects.requireNonNull(whois, "whois");
        Objects.requireNonNull(channelId, "channelId");

        WHOIS.put(whois, channelId);
        CHANNEL.put(channelId, whois);
    }

    public static String get(ChannelId channelId) {
        if (channelId == null) {
            return null;
        }
        return CHANNEL.get(channelId);
    }

    public static ChannelId get(String whois) {
        if (whois == null) {
            return null;
        }
        return WHOIS.get(whois);
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }
        String whois = CHANNEL.get(channelId);
        if (whois != null) {
            WHOIS.remove(whois);
        }
        CHANNEL.remove(channelId);
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }
        ChannelId channelId = WHOIS.get(whois);
        if (channelId != null) {
            CHANNEL.remove(channelId);
        }
        WHOIS.remove(whois);
    }
}
