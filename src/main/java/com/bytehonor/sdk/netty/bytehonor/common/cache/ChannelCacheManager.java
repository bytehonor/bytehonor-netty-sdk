package com.bytehonor.sdk.netty.bytehonor.common.cache;

import java.util.Objects;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class ChannelCacheManager {

    public static void add(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        ChannelId channelId = channel.id();
        if (ChannelCacheHolder.get(channelId) == null) {
            ChannelCacheHolder.add(channel);
        }
    }

    public static int size() {
        return ChannelCacheHolder.size();
    }

    public static void put(String whois, Channel channel) {
        Objects.requireNonNull(whois, "whois");
        Objects.requireNonNull(channel, "channel");

        ChannelId channelId = channel.id();
        if (ChannelCacheHolder.get(channelId) == null) {
            ChannelCacheHolder.add(channel);
        }
        ChannelWhoisCacheHolder.put(whois, channelId);
    }

    public static Channel getChannel(String whois) {
        Objects.requireNonNull(whois, "whois");

        ChannelId channelId = ChannelWhoisCacheHolder.get(whois);
        if (channelId == null) {
            return null;
        }
        Channel channel = ChannelCacheHolder.get(channelId);
        if (channel == null) {
            ChannelWhoisCacheHolder.remove(whois);
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

        return ChannelWhoisCacheHolder.get(whois);
    }

    public static void remove(Channel channel) {
        if (channel == null) {
            return;
        }

        ChannelWhoisCacheHolder.remove(channel.id());
        ChannelCacheHolder.remove(channel);
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }

        ChannelId channelId = ChannelWhoisCacheHolder.get(whois);
        ChannelCacheHolder.remove(ChannelCacheHolder.get(channelId));
        ChannelWhoisCacheHolder.remove(whois);
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }

        ChannelCacheHolder.remove(ChannelCacheHolder.get(channelId));
        ChannelWhoisCacheHolder.remove(channelId);
    }
}
