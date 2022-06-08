package com.bytehonor.sdk.beautify.netty.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.listener.DefaultClientListener;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyClientContanierTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanierTest.class);

    @Test
    public void test() {
        String host = "vpn.bytehonor.com";
        int port = 85;

        try {
            NettyClientContanier.connect(host, port, new DefaultClientListener());
            Thread.sleep(60000L);
            NettyClientContanier.send(NettyPayload.build("hello world"));
            Thread.sleep(60000L * 10);
        } catch (Exception e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }

}
