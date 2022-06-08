package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public interface PayloadHandler {

    public String subject();

    public void handle(NettyPayload payload);
}
