package com.bytehonor.sdk.beautify.netty.common.util;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyChannelUtils {

    private static final String PREFIX = "fixed-stamp-";

    private static final AtomicLong AL = new AtomicLong(0);

    public static String stamp(Channel channel) {
        return channel.id().asLongText();
    }

    public static String stamp() {
        return new StringBuilder().append(PREFIX).append(AL.incrementAndGet()).toString();
    }

    public static String remoteAddress(Channel channel) {
        if (channel == null) {
            return "unknown";
        }
        SocketAddress remoteAddress = channel.remoteAddress();
        if (remoteAddress == null) {
            return "unknown";
        }
        return remoteAddress.toString();
    }
}
