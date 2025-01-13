package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;

public class NettyClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientTest.class);

    public static void main(String[] args) {
        NettyClient client = NettyClient.builder("www.bytehonor.com", 95).handler(new NettyClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("onOpen, stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.of("hello server"));
            }

            @Override
            public void onClosed(String stamp, String msg) {
                LOG.warn("onClosed stamp:{}, msg:{}", stamp, msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("onError", error);
            }

            @Override
            public void onMessage(String stamp, String text) {
                LOG.info("onMessage, stamp:{}, text:{}", stamp, text);
            }

        }).build();

        client.run();
        LOG.info("run");

        NettySleeper.sleep(1000L * 600);
    }

}
