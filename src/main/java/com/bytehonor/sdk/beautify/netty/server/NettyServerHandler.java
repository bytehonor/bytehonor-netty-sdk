package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public interface NettyServerHandler {

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onMessage(NettyMessage message);

    public void onDisconnected(String stamp);

    public void onConnected(String stamp);

}
