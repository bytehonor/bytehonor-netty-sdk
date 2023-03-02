package com.bytehonor.sdk.beautify.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClient;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyServerHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.server.NettyServer;

public class NettyDemo {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDemo.class);

    public static void main(String[] args) {

        startServer();

        try {
            Thread.sleep(1000L * 5);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyClient client = new NettyClient("127.0.0.1", 85, new NettyClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("onOpen stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.build("hello server"));
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
            LOG.info("client.run()");
            client.run();
        } catch (Exception e) {
            LOG.error("error", e);
        }

        try {
            Thread.sleep(1000L * 15);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        try {
            LOG.info("client.run() again");
            client.run();
        } catch (Exception e) {
            LOG.error("error", e);
        }

        try {
            Thread.sleep(1000L * 360);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }
    }

    private static void startServer() {
        new NettyServer(new NettyServerHandler() {

            @Override
            public void onSucceed() {
                LOG.info("Server onSucceed");
            }

            @Override
            public void onFailed(Throwable error) {
                LOG.error("Server onFailed", error);
            }

            @Override
            public void onTotal(int total) {
                LOG.info("Server onTotal:{}", total);
            }

            @Override
            public void onMessage(NettyMessage message) {
                LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
            }

            @Override
            public void onDisconnected(String stamp) {
                LOG.warn("Server onDisconnected stamp:{}", stamp);
            }

            @Override
            public void onConnected(String stamp) {
                LOG.info("Server onConnected stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.build("hello client"));
            }

        }).start();

    }
}
