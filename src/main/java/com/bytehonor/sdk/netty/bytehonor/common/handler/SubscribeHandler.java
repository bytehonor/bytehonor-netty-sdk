package com.bytehonor.sdk.netty.bytehonor.common.handler;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;

public interface SubscribeHandler {

    public String category();

    public void handle(NettyPayload payload);
}
