package com.bytehonor.sdk.beautify.netty.client;

/**
 * @author lijianqiang
 *
 */
public interface NettyClientHandler {

    public void onOpen(String stamp);

    public void onClosed(String stamp, String msg);

    public void onError(String stamp, Throwable error);

    public void onMessage(String stamp, String text);
}
