package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
@Deprecated
public class WhoisChannelCacheHolder {

    private static final int CAP = NettyConstants.CAPACITY;

    /**
     * whois channelId
     */
    private static final ConcurrentHashMap<String, ChannelId> MAP = new ConcurrentHashMap<String, ChannelId>(CAP);

    public static int size() {
        return MAP.size();
    }

    public static void put(String whois, ChannelId channelId) {
        Objects.requireNonNull(whois, "whois");
        Objects.requireNonNull(channelId, "channelId");

        MAP.put(whois, channelId);
    }

    public static String getWhois(ChannelId channelId) {
        String whois = "";
        if (channelId == null) {
            return whois;
        }

        for (Entry<String, ChannelId> item : MAP.entrySet()) {
            if (channelId.equals(item.getValue())) {
                whois = item.getKey();
                break;
            }
        }
        return whois;
    }

    public static ChannelId getChannel(String whois) {
        if (whois == null) {
            return null;
        }

        return MAP.get(whois);
    }

    public static void remove(ChannelId channelId) {
        if (channelId == null) {
            return;
        }
        String whois = getWhois(channelId);
        if (whois != null) {
            MAP.remove(whois);
        }
    }

    public static void remove(String whois) {
        if (whois == null) {
            return;
        }
        MAP.remove(whois);
    }
}
