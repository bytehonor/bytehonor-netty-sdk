package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class ClientListenerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ClientListenerHelper.class);

    public static void onConnect(ClientListener listener, Channel channel) {
        try {
            if (listener != null) {
                listener.onConnect(channel);
            }
        } catch (Exception e) {
            LOG.error("onConnect", e);
        }
    }

    public static void onDisconnect(ClientListener listener, String msg) {
        try {
            if (listener != null) {
                listener.onDisconnect(msg);
            }
        } catch (Exception e) {
            LOG.error("onDisconnect", e);
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
}
