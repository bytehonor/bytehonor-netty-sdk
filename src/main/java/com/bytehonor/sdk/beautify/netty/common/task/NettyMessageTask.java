package com.bytehonor.sdk.beautify.netty.common.task;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageProcessor;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public class NettyMessageTask extends NettyTask {

    private final NettyMessage message;

    private final NettyConsumerFactory factory;

    public NettyMessageTask(NettyMessage message, NettyConsumerFactory factory) {
        this.message = message;
        this.factory = factory;
    }

    public static NettyMessageTask of(NettyMessage message, NettyConsumerFactory factory) {
        return new NettyMessageTask(message, factory);
    }

    @Override
    public void runInSafe() {
        NettyMessageProcessor.process(message, factory);
    }
}
