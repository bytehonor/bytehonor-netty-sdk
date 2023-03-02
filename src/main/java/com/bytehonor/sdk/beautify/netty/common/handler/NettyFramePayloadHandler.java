package com.bytehonor.sdk.beautify.netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyFramePayloadHandler implements NettyFrameHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyFramePayloadHandler.class);

    @Override
    public String method() {
        return NettyFrame.PAYLOAD;
    }

    @Override
    public void handle(String stamp, NettyPayload payload, NettyConsumerFactory factory) {
        NettyConsumer consumer = factory.get(payload.getSubject());
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, stamp:{}", payload.getSubject(), stamp);
            return;
        }
        consumer.consume(payload);
    }

}
