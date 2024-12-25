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

    public static String remarkChannel(Channel channel) {
        StringBuilder sb = new StringBuilder();
        sb.append("remoteAddress:").append(remoteAddress(channel));
        if (channel != null) {
            sb.append(", channelId:").append(channel.id().asLongText());
        }
        return sb.toString();
    }

    public static String remarkMsg(Object msg) {
        if (msg == null) {
            return "null";
        }
        return msg.toString();
    }
}
