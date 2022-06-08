package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PayloadHandlerFactory {

    private static final Map<String, PayloadHandler> MAP = new ConcurrentHashMap<String, PayloadHandler>();

    public static Set<Entry<String, PayloadHandler>> entries() {
        return MAP.entrySet();
    }

    public static Set<String> subjects() {
        return MAP.keySet();
    }

    public static void put(PayloadHandler handler) {
        if (handler == null || handler.subject() == null) {
            return;
        }
        MAP.put(handler.subject(), handler);
    }

    public static PayloadHandler get(String subject) {
        if (subject == null) {
            return null;
        }
        return MAP.get(subject);
    }
}
