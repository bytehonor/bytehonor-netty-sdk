package com.bytehonor.sdk.beautify.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClient;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.listener.AbstractClientHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.AbstractServerHandler;
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

        NettyClient client = new NettyClient("127.0.0.1", 85, new AbstractClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("Client onOpen stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.transfer("hello server"));
            }

            @Override
            public void onClosed(String msg) {
                LOG.warn("Client onClosed:{}", msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("Client onError", error);
            }

            @Override
            public void onPorcess(String stamp, NettyPayload payload) {
                LOG.info("Client onPorcess subject:{}, body:{}, stamp:{}", payload.getSubject(), payload.getBody(), stamp);
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

//        try {
//            LOG.info("client.run() again");
//            client.run();
//        } catch (Exception e) {
//            LOG.error("error", e);
//        }

        try {
            Thread.sleep(1000L * 600);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }
    }

    private static void startServer() {
        new NettyServer(new AbstractServerHandler() {

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

//            @Override
//            public void onMessage(NettyMessage message) {
//                LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
//            }

            @Override
            public void onDisconnected(String stamp) {
                LOG.warn("Server onDisconnected stamp:{}", stamp);
            }

            @Override
            public void onConnected(String stamp) {
                LOG.info("Server onConnected stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.transfer("hello client"));
            }

            @Override
            public void onPorcess(String stamp, NettyPayload payload) {
                LOG.info("Server onPorcess subject:{}, body:{}, stamp:{}", payload.getSubject(), payload.getBody(),
                        stamp);
            }

        }).start();

    }
}
