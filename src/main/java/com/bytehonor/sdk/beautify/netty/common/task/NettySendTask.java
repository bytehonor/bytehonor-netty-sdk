package com.bytehonor.sdk.beautify.netty.common.task;

import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettySendTask extends NettyTask {

    private final String stamp;

    private final NettyPayload payload;

    public NettySendTask(String stamp, NettyPayload payload) {
        this.stamp = stamp;
        this.payload = payload;
    }

    public static NettySendTask of(String stamp, NettyPayload payload) {
        return new NettySendTask(stamp, payload);
    }

    @Override
    public void runInSafe() {
        NettyMessageSender.send(stamp, payload);
    }

}
