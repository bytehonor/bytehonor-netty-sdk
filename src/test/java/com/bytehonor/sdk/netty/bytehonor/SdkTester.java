package com.bytehonor.sdk.netty.bytehonor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.client.NettyClientContanier;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServer;

public class SdkTester {

    private static final Logger LOG = LoggerFactory.getLogger(SdkTester.class);

    @Test
    public void test() {
        NettyConfig sc = new NettyConfig();
        sc.setWhoiam("server");
        new NettyServer().start(sc);

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyConfig cc = new NettyConfig();
        cc.setWhoiam("client");
        try {
            NettyClientContanier.connect(cc);
            Thread.sleep(1000L);
            NettyClientContanier.send(NettyPayload.fromOne("hello world"));
            Thread.sleep(60000L * 10);
        } catch (Exception e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);

        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }
}
