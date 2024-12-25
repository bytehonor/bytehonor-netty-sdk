package com.bytehonor.sdk.beautify.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClient;
import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;
import com.bytehonor.sdk.beautify.netty.server.NettyServer;

public class NettyIdleDemo {

    private static final Logger LOG = LoggerFactory.getLogger(NettyIdleDemo.class);

    public static void main(String[] args) {
        NettyServer server = NettyServer.builder(85).build();
        server.start();
        LOG.info("server.start()");

        NettySleeper.sleep(1000L * 5);

        NettyClient client = NettyClient.builder("127.0.0.1", 85).build();
        client.connect();
        LOG.info("client.connect()");

        NettySleeper.sleep(1000L * 15);

        LOG.info("1. client.isConnected():{}", client.isConnected());

        NettySleeper.sleep(1000L * 120);

        LOG.info("2. client.isConnected():{}", client.isConnected());
    }
}
