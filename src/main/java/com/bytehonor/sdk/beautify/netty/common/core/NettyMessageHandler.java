package com.bytehonor.sdk.beautify.netty.common.core;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public interface NettyMessageHandler {

    public void handle(NettyMessage message);
}
