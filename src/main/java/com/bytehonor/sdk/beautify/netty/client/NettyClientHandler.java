package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public interface NettyClientHandler {

    public void onOpen(String stamp);

    public void onClosed(String stamp, String msg);

    public void onError(String stamp, Throwable error);

    public void onMessage(NettyMessage message);
}
