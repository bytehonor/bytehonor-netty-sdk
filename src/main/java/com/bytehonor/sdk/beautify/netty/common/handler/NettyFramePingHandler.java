package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFramePack;

/**
 * @author lijianqiang
 *
 */
public class NettyFramePingHandler implements NettyFrameHandler {

    @Override
    public String method() {
        return NettyFrame.PING;
    }

    @Override
    public void handle(NettyFramePack pack, NettyConsumerFactory factory) {
        NettyMessageSender.pong(pack.getStamp());
    }

}
