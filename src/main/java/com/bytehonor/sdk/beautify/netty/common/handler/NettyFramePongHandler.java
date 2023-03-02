package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

/**
 * @author lijianqiang
 *
 */
public class NettyFramePongHandler implements NettyFrameHandler {

    @Override
    public String method() {
        return NettyFrame.PONG;
    }

    @Override
    public void handle(String stamp, NettyPayload payload, NettyConsumerFactory factory) {

    }

}
