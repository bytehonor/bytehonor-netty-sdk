package com.bytehonor.sdk.netty.bytehonor.common.cache;

import java.util.Objects;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class ChannelCacheManager {

    public static void add(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        ChannelId channelId = channel.id();
        if (ClientChannelCacheHolder.get(channelId) == null) {
            ClientChannelCacheHolder.add(channel);
        }
    }

    public static int size() {
        return ClientChannelCacheHolder.size();
    }

    public static void put(String whois, Channel channel) {
        Objects.requireNonNull(whois, "whois");
        Objects.requireNonNull(channel, "channel");

        ChannelId channelId = channel.id();
        if (ClientChannelCacheHolder.get(channelId) == null) {
            ClientChannelCacheHolder.add(channel);
        }
        WhoisChannelCacheHolder.put(whois, channelId);
    }

    public static Channel getChannel(String whois) {
        Objects.requireNonNull(whois, "whois");

        ChannelId channelId = WhoisChannelCacheHolder.get(whois);
        if (channelId == null) {
            return null;
        }
        Channel channel = ClientChannelCacheHolder.get(channelId);
        if (channel == null) {
            WhoisChannelCacheHolder.remove(whois);
            return null;
        }
        return channel;
    }

    public static Channel getChannel(ChannelId channelId) {
        Objects.requireNonNull(channelId, "channelId");

        return ClientChannelCacheHolder.get(channelId);
    }

    public static ChannelId getChannelId(String whois) {
        Objects.requireNonNull(whois, "whois");

        return WhoisChannelCacheHolder.get(whois);
    }

    public static void remove(Channel channel) {
        if (channel == null) {
            return;
        }

        WhoisChannelCacheHolder.remove(channel.id());
        ClientChannelCacheHolder.remove(channel);
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }

        ChannelId channelId = WhoisChannelCacheHolder.get(whois);
        ClientChannelCacheHolder.remove(ClientChannelCacheHolder.get(channelId));
        WhoisChannelCacheHolder.remove(whois);
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }

        ClientChannelCacheHolder.remove(ClientChannelCacheHolder.get(channelId));
        WhoisChannelCacheHolder.remove(channelId);
    }
}
