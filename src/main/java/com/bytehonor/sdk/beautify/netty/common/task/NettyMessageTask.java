package com.bytehonor.sdk.beautify.netty.common.task;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerGetter;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageProcessor;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public class NettyMessageTask extends NettyTask {

    private final NettyMessage message;

    private final NettyConsumerGetter getter;

    public NettyMessageTask(NettyMessage message, NettyConsumerGetter getter) {
        this.message = message;
        this.getter = getter;
    }

    public static NettyMessageTask of(NettyMessage message, NettyConsumerGetter getter) {
        return new NettyMessageTask(message, getter);
    }

    @Override
    public void runInSafe() {
        NettyMessageProcessor.process(message, getter);
    }
}
