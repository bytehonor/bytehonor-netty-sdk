package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * 独立线程
 * 
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    private static final AtomicLong AL = new AtomicLong(0);

    private final NettyConsumerFactory consumers;

    /**
     * 队列
     */
    private final LinkedBlockingQueue<NettyMessage> queue;
    /**
     * 线程
     */
    private final Thread thread;

    public NettyMessageReceiver() {
        this(51200);
    }

    public NettyMessageReceiver(int queues) {
        consumers = new NettyConsumerFactory();
        queue = new LinkedBlockingQueue<NettyMessage>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyMessage message = queue.take();
                        process(message);
                    } catch (Exception e) {
                        LOG.error("runInSafe error", e);
                    }
                }
            }

        });

        thread.setName(NettyMessageReceiver.class.getSimpleName() + "-" + AL.incrementAndGet());
        thread.start();
        LOG.info("[Thread] {} start, queues:{}", thread.getName(), queues);
    }

    public final void addConsumer(NettyConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");
        this.consumers.add(consumer);
    }

    private void process(NettyMessage message) {
        String stamp = message.getStamp();
        NettyFrame frame = NettyFrame.fromJson(message.getText());
        if (LOG.isDebugEnabled()) {
            LOG.debug("process method:{}, subject:{}, stamp:{}", frame.getMethod(), frame.getSubject(), stamp);
        }

        final String method = frame.getMethod();
        if (method == null) {
            LOG.error("method null, message:{}", message.getText());
            return;
        }

        switch (method) {
        case NettyFrame.PING:
            doPing(stamp);
            break;
        case NettyFrame.PONG:
            doPong(stamp);
            break;
        case NettyFrame.PAYLOAD:
            doPayload(stamp, frame);
            break;
        default:
            LOG.warn("unkonwn method:{}", method);
            break;
        }
    }

    private void doPayload(String stamp, NettyFrame frame) {
        NettyConsumer consumer = consumers.get(frame.getSubject());
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", frame.getSubject(), frame.beautifyBody(), stamp);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(frame.getSubject(), frame.getBody()));
    }

    private void doPong(String stamp) {
        LOG.debug("stamp:{}", stamp);
    }

    private void doPing(String stamp) {
        NettyMessageSender.pong(stamp);
    }

    public final void addMessage(NettyMessage message) {
        if (message == null) {
            LOG.warn("message null");
            return;
        }
        try {
            queue.put(message);
        } catch (Exception e) {
            LOG.error("addMessage error", e);
        }
    }
}
