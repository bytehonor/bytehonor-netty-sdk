package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public interface NettyConsumer {

    public String subject();

    public void consume(NettyPayload payload);
}
