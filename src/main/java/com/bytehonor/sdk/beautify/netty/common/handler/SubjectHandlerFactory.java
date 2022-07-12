package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SubjectHandlerFactory {

    private static final Map<String, SubjectHandler> MAP = new ConcurrentHashMap<String, SubjectHandler>();

    public static Set<Entry<String, SubjectHandler>> entries() {
        return MAP.entrySet();
    }

    public static Set<String> subjects() {
        return MAP.keySet();
    }

    public static void put(SubjectHandler handler) {
        Objects.requireNonNull(handler, "handler");

        MAP.put(handler.subject(), handler);
    }

    public static SubjectHandler get(String subject) {
        Objects.requireNonNull(subject, "subject");

        return MAP.get(subject);
    }
}
