package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFramePack;

/**
 * @author lijianqiang
 *
 */
public interface NettyFrameHandler {

    public String method();

    public void handle(NettyFramePack pack, NettyConsumerFactory factory);
}
