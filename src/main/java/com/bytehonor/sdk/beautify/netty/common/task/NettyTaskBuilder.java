package com.bytehonor.sdk.beautify.netty.common.task;

public class NettyTaskBuilder {

    public static NettyTask serverCheck() {
        return new NettyServerCheckTask();
    }
    
    public static NettyTask clientPing() {
        return new NettyClientPingTask();
    }
}
