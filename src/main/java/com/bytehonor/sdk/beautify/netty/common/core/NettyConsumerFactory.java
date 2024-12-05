package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijianqiang
 *
 */
public class NettyConsumerFactory {

    private static final Logger LOG = LoggerFactory.getLogger(NettyConsumerFactory.class);

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

        String subject = consumer.subject();
        LOG.info("subject:{}", subject);
        map.put(subject, consumer);
    }

    public NettyConsumer get(String subject) {
        Objects.requireNonNull(subject, "subject");

        return map.get(subject);
    }
}
