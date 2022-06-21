package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public interface SubjectHandler {

    public String subject();

    public void handle(NettyPayload payload);
}
