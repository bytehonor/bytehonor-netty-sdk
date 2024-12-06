package com.bytehonor.sdk.beautify.netty.common.util;

import java.net.SocketAddress;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyChannelUtils {

    private static final String PREFIX = "netty-stamp-";

    public static String stamp(Channel channel) {
        return channel.id().asLongText();
    }

    public static String stamp(String host, int port) {
        return new StringBuilder().append(PREFIX).append(host).append(":").append(port).toString();
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
