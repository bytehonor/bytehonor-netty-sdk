package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerExecutor;

import io.netty.channel.Channel;

public class NettyPayloadHandler implements NettyHandler {

    @Override
    public int type() {
        return NettyTypeEnum.PUBLIC_PAYLOAD.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        // 单线程处理
        NettyConsumerExecutor.add(message);
    }

}
