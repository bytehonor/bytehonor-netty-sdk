package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscribeHandlerFactory {

    private static final Map<String, SubscribeHandler> MAP = new ConcurrentHashMap<String, SubscribeHandler>();

    public static void put(SubscribeHandler handler) {
        if (handler == null) {
            return;
        }
        MAP.put(handler.category(), handler);
    }

    public static SubscribeHandler get(String category) {
        if (category == null) {
            return null;
        }
        return MAP.get(category);
    }
}
