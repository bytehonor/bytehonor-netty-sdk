package com.bytehonor.sdk.netty.bytehonor.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.client.NettyClientContanier;

public class NettyClientPingTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientPingTask.class);

    @Override
    public void run() {
        try {
            NettyClientContanier.ping();
        } catch (Exception e) {
            LOG.error("ping error:{}", e.getMessage());
            NettyClientContanier.reconnect();
        }
    }

}
