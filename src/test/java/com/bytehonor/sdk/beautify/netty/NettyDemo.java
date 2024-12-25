package com.bytehonor.sdk.beautify.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.AbstractClientHandler;
import com.bytehonor.sdk.beautify.netty.client.NettyClient;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;
import com.bytehonor.sdk.beautify.netty.server.AbstractServerHandler;
import com.bytehonor.sdk.beautify.netty.server.NettyServer;

public class NettyDemo {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDemo.class);

    public static void main(String[] args) {

        startServer();

        NettySleeper.sleep(1000L * 5);

        NettyClient client = NettyClient.builder("127.0.0.1", 85).handler(new AbstractClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("Client onOpen stamp:{}", stamp);
                NettyMessageSender.send(stamp, NettyPayload.of("hello server"));
            }

            @Override
            public void onClosed(String stamp, String msg) {
                LOG.warn("Client onClosed stamp:{}", stamp, msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("Client onError stamp:{}, error", stamp, error);
            }
        }).build();

        LOG.info("client.run()");
        client.run();

        NettySleeper.sleep(1000L * 15);

//        try {
//            LOG.info("client.run() again");
//            client.run();
//        } catch (Exception e) {
//            LOG.error("error", e);
//        }

        NettySleeper.sleep(1000L * 600);
    }

    private static void startServer() {
        NettyServer server = NettyServer.builder(85).handler(new AbstractServerHandler() {

            @Override
            public void onStarted() {
                LOG.info("Server onStarted");
            }

            @Override
            public void onFailed(Throwable error) {
                LOG.error("Server onFailed", error);
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
                NettyMessageSender.send(stamp, NettyPayload.of("hello client"));
            }

        }).build();
        server.start();

    }
}
