package com.bytehonor.sdk.beautify.netty.common.util;

import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyStampGenerator {

    private static final String PREFIX = "fixed-stamp-";

    private static final AtomicLong AL = new AtomicLong(0);

    public static String stamp(Channel channel) {
        return channel.id().asLongText();
    }

    public static String stamp() {
        return new StringBuilder().append(PREFIX).append(AL.incrementAndGet()).toString();
    }
}
