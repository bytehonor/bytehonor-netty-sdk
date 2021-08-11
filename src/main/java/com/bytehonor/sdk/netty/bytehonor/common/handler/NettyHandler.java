package com.bytehonor.sdk.netty.bytehonor.common.handler;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyContent;

import io.netty.channel.Channel;

public interface NettyHandler {

    public String matchCmd();

    public void handle(Channel channel, NettyContent content);
}
