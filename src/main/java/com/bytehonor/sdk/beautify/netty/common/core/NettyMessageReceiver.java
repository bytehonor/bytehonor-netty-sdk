package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyMessageTask;

/**
 * 放入线程池处理
 * 
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver implements NettyMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    private static final AtomicInteger AI = new AtomicInteger(0);

    private static final String NAMED = "netty-message-receiver-%s-";

    private final ExecutorService service;

    private final NettyConsumerFactory factory;

    public NettyMessageReceiver() {
        int nThreads = Runtime.getRuntime().availableProcessors();
        String name = String.format(NAMED, AI.incrementAndGet());
        this.service = Executors.newFixedThreadPool(nThreads, new CustomizableThreadFactory(name));
        this.factory = new NettyConsumerFactory();
    }

    public static void main(String[] args) {
        System.out.println(String.format(NAMED, AI.incrementAndGet()));
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
        service.execute(NettyMessageTask.of(message, this));
    }

    @Override
    public final void handle(NettyMessage message) {
        final String stamp = message.getStamp();
        final NettyFrame frame = NettyFrame.fromJson(message.getFrame());
        if (LOG.isDebugEnabled()) {
            LOG.debug("process method:{}, subject:{}, stamp:{}", frame.getMethod(), frame.getSubject(), stamp);
        }

        final String method = frame.getMethod();
        if (method == null) {
            LOG.error("method null, message:{}", message.getFrame());
            return;
        }

        switch (method) {
        case NettyFrame.PING:
            NettyMessageSender.pong(stamp);
            break;
        case NettyFrame.PONG:
            doPong(stamp);
            break;
        case NettyFrame.PAYLOAD:
            doProcess(stamp, frame);
            break;
        default:
            LOG.warn("unkonwn method:{}", method);
            break;
        }
    }

    private void doPong(String stamp) {
        LOG.debug("stamp:{}", stamp);
    }

    private void doProcess(String stamp, NettyFrame frame) {
        String subject = frame.getSubject();
        NettyConsumer consumer = factory.get(subject);
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", subject, frame.beautifyBody(), stamp);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(subject, frame.getBody()));
    }
}
