package com.bytehonor.sdk.beautify.netty.common.core;

import org.junit.Test;

import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;
import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;
import com.bytehonor.sdk.beautify.netty.sample.SampleUser;
import com.bytehonor.sdk.beautify.netty.sample.SampleUserNettyConsumer;

public class NettyMessageReceiverTest {

    @Test
    public void test() {
        NettyMessageReceiver receiver = new NettyMessageReceiver();

        receiver.addConsumer(new SampleUserNettyConsumer());

        for (int i = 0; i < 10; i++) {
            receiver.onMessage(mock(i));
        }

        NettySleeper.sleep(5000L);
    }

    private NettyMessage mock(int i) {
        NettyPayload payload = NettyPayload.of(SampleUser.of(i));
        return NettyMessage.of("stamp-test", NettyJsonUtils.toJson(NettyFrame.payload(payload)));
    }
}
