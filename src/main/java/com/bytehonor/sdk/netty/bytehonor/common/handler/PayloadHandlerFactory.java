package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PayloadHandlerFactory {

    private static final Map<String, PayloadHandler> MAP = new ConcurrentHashMap<String, PayloadHandler>();

    public static Set<Entry<String, PayloadHandler>> entries() {
        return MAP.entrySet();
    }

    public static Set<String> names() {
        return MAP.keySet();
    }

    public static void put(PayloadHandler handler) {
        if (handler == null) {
            return;
        }
        MAP.put(handler.name(), handler);
    }

    public static PayloadHandler get(String category) {
        if (category == null) {
            return null;
        }
        return MAP.get(category);
    }
}
