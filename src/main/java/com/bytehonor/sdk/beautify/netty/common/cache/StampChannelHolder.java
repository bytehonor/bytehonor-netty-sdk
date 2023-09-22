package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class StampChannelHolder {

    private static final int CAP = NettyConstants.CAPACITY;

    /**
     * stamp channel
     */
    private static final ConcurrentHashMap<String, Channel> MAP = new ConcurrentHashMap<String, Channel>(CAP);

    public static int size() {
        return MAP.size();
    }

    public static void put(String stamp, Channel channel) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(channel, "channel");

        MAP.put(stamp, channel);
    }

    public static Channel get(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        return MAP.get(stamp);
    }

    public static void remove(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        MAP.remove(stamp);
    }

    public static boolean has(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        return MAP.containsKey(stamp);
    }
}
