package com.bytehonor.sdk.netty.bytehonor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.client.NettyClientContanier;
import com.bytehonor.sdk.netty.bytehonor.common.listener.ClientListener;
import com.bytehonor.sdk.netty.bytehonor.common.listener.DefaultServerListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServerContanier;

import io.netty.channel.Channel;

public class IdleTest {

    private static final Logger LOG = LoggerFactory.getLogger(IdleTest.class);

    @Test
    public void test() {
        NettyConfig server = new NettyConfig();
        server.setWhoiam("server");
        server.setAllIdleSeconds(20);
        NettyServerContanier.start(server, new DefaultServerListener());

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyConfig client = new NettyConfig();
        client.setWhoiam("client");
        client.setAllIdleSeconds(15);
        try {
            NettyClientContanier.connect(client, new ClientListener() {

                @Override
                public void onOpen(Channel channel) {
                    LOG.info("onOpen");
                    NettyClientContanier.send(NettyPayload.build("hello world"));
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
            Thread.sleep(1000L * 80);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }
}
