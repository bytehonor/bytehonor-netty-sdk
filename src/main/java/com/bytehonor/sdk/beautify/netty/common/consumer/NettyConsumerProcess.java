package com.bytehonor.sdk.beautify.netty.common.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyConsumerProcess {

    private static final Logger LOG = LoggerFactory.getLogger(NettyConsumerProcess.class);

    public static void process(String stamp, NettyFrame frame, NettyConsumer consumer) {
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", frame.getSubject(), frame.beautifyBody(), stamp);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(frame.getSubject(), frame.getBody()));
    }
}
