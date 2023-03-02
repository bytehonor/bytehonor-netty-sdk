package com.bytehonor.sdk.beautify.netty.common.listener;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public interface NettyServerHandler {

    public void onMessage(NettyMessage message);

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onTotal(int total);

    public void onDisconnected(String stamp);

    public void onConnected(String stamp);

}
