package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NettyConsumerFactory {

    private static final Map<String, NettyConsumer> MAP = new ConcurrentHashMap<String, NettyConsumer>();

    public static Set<Entry<String, NettyConsumer>> entries() {
        return MAP.entrySet();
    }

    public static Set<String> subjects() {
        return MAP.keySet();
    }

    public static void put(NettyConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");

        MAP.put(consumer.subject(), consumer);
    }

    public static NettyConsumer get(String subject) {
        Objects.requireNonNull(subject, "subject");

        return MAP.get(subject);
    }
}
