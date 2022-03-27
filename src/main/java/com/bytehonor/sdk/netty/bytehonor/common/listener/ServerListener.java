package com.bytehonor.sdk.netty.bytehonor.common.listener;

public interface ServerListener {

    public void onSucceed();

    public void onFailed(Throwable error);

    public void onTotal(int total);

}
