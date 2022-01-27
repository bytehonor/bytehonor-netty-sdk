package com.bytehonor.sdk.netty.bytehonor.common.task;

public class NettyTaskBuilder {

    public static Runnable serverCheck() {
        return new NettyServerCheckTask();
    }
    
    public static Runnable clientPing() {
        return new NettyClientPingTask();
    }
}
