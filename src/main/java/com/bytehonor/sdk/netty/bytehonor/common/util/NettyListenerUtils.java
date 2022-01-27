package com.bytehonor.sdk.netty.bytehonor.common.util;

import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListener;

import io.netty.channel.Channel;

public class NettyListenerUtils {

    public static void onOpen(NettyListener listener, Channel channel) {
        if (listener != null) {
            listener.onOpen(channel);
        }
    }

    public static void onClosed(NettyListener listener, String msg) {
        if (listener != null) {
            listener.onClosed(msg);
        }
    }

    public static void onError(NettyListener listener, Throwable error) {
        if (listener != null) {
            listener.onError(error);
        }
    }
}
