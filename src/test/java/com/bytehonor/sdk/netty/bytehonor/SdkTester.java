package com.bytehonor.sdk.netty.bytehonor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.client.NettyClientContanier;
import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServer;

import io.netty.channel.Channel;

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
            NettyClientContanier.connect(cc, new NettyListener() {

                @Override
                public void onOpen(Channel channel) {
                    LOG.info("onOpen");
                    NettyClientContanier.send(NettyPayload.fromOne("hello world"));
                    NettyClientContanier.ping();
                }

                @Override
                public void onError(Throwable error) {
                    LOG.error("onError", error);
                }

                @Override
                public void onClosed(String msg) {
                    LOG.warn("onClosed:{}", msg);
                }
            });
            Thread.sleep(1000L);
        } catch (Exception e) {
            LOG.error("error", e);
        }

        try {
            Thread.sleep(1000L * 60);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }
}
