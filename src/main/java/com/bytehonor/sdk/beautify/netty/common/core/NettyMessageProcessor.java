package com.bytehonor.sdk.beautify.netty.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyMessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageProcessor.class);

    public static void process(final NettyMessage message, final NettyConsumerFactory factory) {
        String stamp = message.getStamp();
        NettyFrame frame = NettyFrame.fromJson(message.getFrame());
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
            doPing(stamp);
            break;
        case NettyFrame.PONG:
            doPong(stamp);
            break;
        case NettyFrame.PAYLOAD:
            doPayload(stamp, frame, factory);
            break;
        default:
            LOG.warn("unkonwn method:{}", method);
            break;
        }
    }

    private static void doPayload(String stamp, NettyFrame frame, NettyConsumerFactory factory) {
        NettyConsumer consumer = factory.get(frame.getSubject());
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", frame.getSubject(), frame.beautifyBody(), stamp);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(frame.getSubject(), frame.getBody()));
    }

    private static void doPong(String stamp) {
        LOG.debug("stamp:{}", stamp);
    }

    private static void doPing(String stamp) {
        NettyMessageSender.pong(stamp);
    }
}
