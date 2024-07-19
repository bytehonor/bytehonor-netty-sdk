package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.Objects;

import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public class NettyMessageTask extends NettyTask {

    private final NettyMessage message;

    private final NettyMessageHandler handler;

    public NettyMessageTask(NettyMessage message, NettyMessageHandler handler) {
        Objects.requireNonNull(handler, "handler");
        this.message = message;
        this.handler = handler;
    }

    public static NettyMessageTask of(NettyMessage message, NettyMessageHandler handler) {
        return new NettyMessageTask(message, handler);
    }

    @Override
    public void runInSafe() {
        handler.handle(message);
    }
}
