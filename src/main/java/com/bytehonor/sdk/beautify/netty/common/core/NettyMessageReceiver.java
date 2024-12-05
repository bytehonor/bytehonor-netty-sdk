package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * 放入线程池处理
 * 
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    private final NettyConsumerFactory factory;

    public NettyMessageReceiver() {
        this.factory = new NettyConsumerFactory();
    }

    public final void addConsumer(NettyConsumer consumer) {
        Objects.requireNonNull(consumer, "consumer");
        this.factory.add(consumer);
    }

    public final void addMessage(final NettyMessage message) {
        if (message == null) {
            LOG.warn("message null");
            return;
        }
        NettyMessagePoolExecutor.add(new NettyTask() {

            @Override
            public void runInSafe() {
                handle(message);
            }

        });
    }

    private void handle(NettyMessage message) {
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
            doPayload(stamp, frame);
            break;
        default:
            LOG.warn("unkonwn method:{}", method);
            break;
        }
    }

    private void doPong(String stamp) {
        LOG.debug("stamp:{}", stamp);
    }

    private void doPayload(String stamp, NettyFrame frame) {
        String subject = frame.getSubject();
        NettyConsumer consumer = factory.get(subject);
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", subject, frame.beautifyBody(), stamp);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(subject, frame.getBody()));
    }
}
