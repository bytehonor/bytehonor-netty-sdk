package com.bytehonor.sdk.netty.bytehonor.common.handler;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;

public interface PayloadHandler {

    public String name();

    public void handle(NettyPayload payload);
}
