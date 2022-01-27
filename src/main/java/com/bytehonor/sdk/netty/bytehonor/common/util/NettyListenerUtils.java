package com.bytehonor.sdk.netty.bytehonor.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListener;

import io.netty.channel.Channel;

public class NettyListenerUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyListenerUtils.class);

    public static void onOpen(NettyListener listener, Channel channel) {
        try {
            if (listener != null) {
                listener.onOpen(channel);
            }
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

    public static void onClosed(NettyListener listener, String msg) {
        try {
            if (listener != null) {
                listener.onClosed(msg);
            }
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

    public static void onError(NettyListener listener, Throwable error) {
        try {
            if (listener != null) {
                listener.onError(error);
            }
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }
}
