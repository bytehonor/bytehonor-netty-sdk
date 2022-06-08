package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.Objects;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class ChannelCacheManager {

    public static void add(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        ChannelCacheHolder.add(channel);
    }

    public static int size() {
        return ChannelCacheHolder.size();
    }

    public static void put(String whois, Channel channel) {
        Objects.requireNonNull(whois, "whois");
        Objects.requireNonNull(channel, "channel");

        ChannelCacheHolder.add(channel);
        WhoisChannelCacheHolder.put(whois, channel.id());
    }

    public static Channel getChannel(String whois) {
        Objects.requireNonNull(whois, "whois");

        ChannelId channelId = getChannelId(whois);
        if (channelId == null) {
            return null;
        }
        Channel channel = getChannel(channelId);
        if (channel == null) {
            WhoisChannelCacheHolder.remove(whois);
            return null;
        }
        return channel;
    }

    public static Channel getChannel(ChannelId channelId) {
        Objects.requireNonNull(channelId, "channelId");

        return ChannelCacheHolder.get(channelId);
    }

    public static ChannelId getChannelId(String whois) {
        Objects.requireNonNull(whois, "whois");

        return WhoisChannelCacheHolder.get(whois);
    }

    public static String getWhois(ChannelId channelId) {
        Objects.requireNonNull(channelId, "channelId");

        return WhoisChannelCacheHolder.get(channelId);
    }

    public static void remove(Channel channel) {
        if (channel == null) {
            return;
        }

        WhoisChannelCacheHolder.remove(channel.id());
        ChannelCacheHolder.remove(channel);
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }

        ChannelId channelId = WhoisChannelCacheHolder.get(whois);
        ChannelCacheHolder.remove(ChannelCacheHolder.get(channelId));
        WhoisChannelCacheHolder.remove(whois);
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }

        ChannelCacheHolder.remove(ChannelCacheHolder.get(channelId));
        WhoisChannelCacheHolder.remove(channelId);
    }
}
