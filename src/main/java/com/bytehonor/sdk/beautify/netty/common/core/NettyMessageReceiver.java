package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerGetter;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.task.NettyMessageTask;

/**
 * 放入线程池处理
 * 
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver implements NettyConsumerGetter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    private final NettyConsumerFactory factory;

    public NettyMessageReceiver() {
        this.factory = new NettyConsumerFactory();
    }

    public final void addConsumer(NettyConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");
        this.factory.add(consumer);
    }

    public final void addMessage(NettyMessage message) {
        if (message == null) {
            LOG.warn("message null");
            return;
        }
        NettyMessagePoolExecutor.add(NettyMessageTask.of(message, this));
    }

    @Override
    public final NettyConsumer get(String subject) {
        return factory.get(subject);
    }

}
