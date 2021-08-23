package com.bytehonor.sdk.netty.bytehonor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientContanierTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanierTest.class);

    @Test
    public void test() {
        String host = "vpn.bytehonor.com";
        int port = 81;

        try {
            NettyClientContanier.connect(host, port);
            Thread.sleep(60000L);
            NettyClientContanier.send("hello world");
            NettyClientContanier.whois();
            Thread.sleep(60000L * 10);
        } catch (Exception e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }

}
