package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ServerListenerHelper.class);

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
