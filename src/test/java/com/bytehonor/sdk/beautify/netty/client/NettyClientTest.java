package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientTest.class);

    public static void main(String[] args) {
        NettyClient client = new NettyClient("www.bytehonor.com", 85, new NettyClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("onOpen, stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.of("hello server"));
            }

            @Override
            public void onClosed(String stamp, String msg) {
                LOG.warn("onClosed stamp:{}", stamp, msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("onError", error);
            }

            @Override
            public void onMessage(String stamp, String text) {
                LOG.info("onMessage, stamp:{}, text:{}", stamp, text);
            }

        });
        LOG.info("run");
        client.run();

    }

}
