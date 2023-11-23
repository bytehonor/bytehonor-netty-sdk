package com.bytehonor.sdk.beautify.netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFramePack;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

/**
 * @author lijianqiang
 *
 */
public class NettyFramePayloadHandler implements NettyFrameHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyFramePayloadHandler.class);

    @Override
    public String method() {
        return NettyFrame.PAYLOAD;
    }

    @Override
    public void handle(NettyFramePack pack, NettyConsumerFactory factory) {
        String stamp = pack.getStamp();
        NettyFrame frame = pack.getFrame();
        NettyConsumer consumer = factory.get(frame.getSubject());
        if (consumer == null) {
            LOG.warn("consumer null, subject:{}, body:{}, stamp:{}", frame.getSubject(), substring(frame.getBody()),
                    frame);
            return;
        }
        consumer.consume(stamp, NettyPayload.of(frame.getSubject(), frame.getBody()));
    }

    private String substring(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.length() > 100 ? text.substring(100) : text;
    }
}
