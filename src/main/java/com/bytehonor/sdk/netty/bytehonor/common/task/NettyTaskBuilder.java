package com.bytehonor.sdk.netty.bytehonor.common.task;

public class NettyTaskBuilder {

    public static NettyTask serverCheck() {
        return new NettyServerCheckTask();
    }
    
    public static NettyTask clientPing() {
        return new NettyClientPingTask();
    }
}
