package com.bytehonor.sdk.beautify.netty.server;

/**
 * @author lijianqiang
 *
 */
public interface NettyServerHandler {

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onMessage(String stamp, String text);

    public void onDisconnected(String stamp);

    public void onConnected(String stamp);

}
