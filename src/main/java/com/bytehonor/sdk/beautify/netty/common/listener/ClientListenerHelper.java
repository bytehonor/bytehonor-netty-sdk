package com.bytehonor.sdk.beautify.netty.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

@Deprecated
public class ClientListenerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ClientListenerHelper.class);

    public static void onOpen(NettyClientHandler listener, Channel channel) {
//        try {
//            if (listener != null) {
//                listener.onOpen(channel);
//            }
//        } catch (Exception e) {
//            LOG.error("onOpen", e);
//        }
    }

    public static void onClosed(NettyClientHandler listener, String msg) {
//        try {
//            if (listener != null) {
//                listener.onClosed(msg);
//            }
//        } catch (Exception e) {
//            LOG.error("onClosed", e);
//        }
    }

    public static void onError(NettyClientHandler listener, Throwable error) {
//        try {
//            if (listener != null) {
//                listener.onError(error);
//            }
//        } catch (Exception e) {
//            LOG.error("onError", e);
//        }
    }
}
