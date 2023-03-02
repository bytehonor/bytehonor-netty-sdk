package com.bytehonor.sdk.beautify.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClient;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyClientSender;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.server.NettyServer;

public class NettyDemo {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDemo.class);

    public static void main(String[] args) {
        new NettyServer().start();

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyClient client = new NettyClient("127.0.0.1", 85, new NettyClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("onOpen");
                NettyClientSender.send(stamp, NettyPayload.build("hello world"));
            }

            @Override
            public void onClosed(String msg) {
                LOG.warn("onClosed:{}", msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("onError", error);
            }

            @Override
            public void onMessage(NettyMessage message) {
                LOG.info("onMessage text:{} stamp:{}", message.getText(), message.getStamp());
            }

        });

        try {
            LOG.info("client.start()");
            client.start();
        } catch (Exception e) {
            LOG.error("error", e);
        }

        try {
            Thread.sleep(1000L * 15);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        try {
            LOG.info("client.close()");
            client.close();
            Thread.sleep(1000L * 15);

            LOG.info("client.start()");
            client.start();
        } catch (Exception e) {
            LOG.error("error", e);
        }

        try {
            Thread.sleep(1000L * 360);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }
    }
}
