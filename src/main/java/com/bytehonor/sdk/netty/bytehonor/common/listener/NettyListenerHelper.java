package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class NettyListenerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(NettyListenerHelper.class);

    public static void onOpen(ClientListener listener, Channel channel) {
        try {
            if (listener != null) {
                listener.onOpen(channel);
            }
        } catch (Exception e) {
            LOG.error("onOpen", e);
        }
    }

    public static void onClosed(ClientListener listener, String msg) {
        try {
            if (listener != null) {
                listener.onClosed(msg);
            }
        } catch (Exception e) {
            LOG.error("onClosed", e);
        }
    }

    public static void onError(ClientListener listener, Throwable error) {
        try {
            if (listener != null) {
                listener.onError(error);
            }
        } catch (Exception e) {
            LOG.error("onError", e);
        }
    }

    public static void onSucceed(ServerListener listener) {
        try {
            if (listener != null) {
                listener.onSucceed();
            }
        } catch (Exception e) {
            LOG.error("onSucceed", e);
        }
    }

    public static void onFailed(ServerListener listener, Throwable error) {
        try {
            if (listener != null) {
                listener.onFailed(error);
            }
        } catch (Exception e) {
            LOG.error("onFailed", e);
        }
    }

    public static void onTotal(ServerListener listener, int total) {
        try {
            if (listener != null) {
                listener.onTotal(total);
            }
        } catch (Exception e) {
            LOG.error("onTotal", e);
        }
    }
}
