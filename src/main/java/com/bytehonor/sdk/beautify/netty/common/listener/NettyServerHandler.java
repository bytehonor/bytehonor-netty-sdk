package com.bytehonor.sdk.beautify.netty.common.listener;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

import io.netty.channel.Channel;

public interface NettyServerHandler {

    public void onMessage(NettyMessage message);

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onTotal(int total);

    public void onDisconnected(Channel channel);

    public void onConnected(Channel channel);

}
