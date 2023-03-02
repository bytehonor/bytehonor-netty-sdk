package com.bytehonor.sdk.beautify.netty.common.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * @author lijianqiang
 *
 */
public class NettyConsumerFactory {

    private final Map<String, NettyConsumer> map;

    public NettyConsumerFactory() {
        this.map = new HashMap<String, NettyConsumer>();
    }

    public Set<Entry<String, NettyConsumer>> entries() {
        return map.entrySet();
    }

    public Set<String> subjects() {
        return map.keySet();
    }

    public void add(NettyConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");

        map.put(consumer.subject(), consumer);
    }

    public NettyConsumer get(String subject) {
        Objects.requireNonNull(subject, "subject");

        return map.get(subject);
    }
}
