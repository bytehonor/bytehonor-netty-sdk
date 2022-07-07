package com.bytehonor.sdk.beautify.netty.common.listener;

public interface NettyServerListener {

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onTotal(int total);

}
