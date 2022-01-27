package com.bytehonor.sdk.netty.bytehonor.common.task;

public class NettyTaskBuilder {

    public static Runnable server() {
        return new NettyServerCheckTask();
    }
}
