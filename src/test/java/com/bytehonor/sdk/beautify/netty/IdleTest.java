package com.bytehonor.sdk.beautify.netty;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClientContanier;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.DefaultNettyServerHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.server.NettyServerContanier;

import io.netty.channel.Channel;

public class IdleTest {

    private static final Logger LOG = LoggerFactory.getLogger(IdleTest.class);

    @Test
    public void test() {
        NettyConfig server = new NettyConfig();
        server.setAllIdleSeconds(20);
       // NettyServerContanier.start(server, new DefaultNettyServerHandler());

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyConfig client = new NettyConfig();
        client.setAllIdleSeconds(15);
//        try {
//            NettyClientContanier.connect(client, new NettyClientHandler() {
//
//                @Override
//                public void onOpen(String stamp) {
//                    LOG.info("onOpen");
////                    NettyClientContanier.send(NettyPayload.build("hello world"));
////                    NettyClientContanier.ping();
//                }
//
//                @Override
//                public void onError(Throwable error) {
//                    LOG.error("onError", error);
//                }
//
//                @Override
//                public void onClosed(String msg) {
//                    LOG.warn("onClosed:{}", msg);
//                }
//
//                @Override
//                public void onMessage(NettyMessage of) {
//                    
//                }
//            });
//            Thread.sleep(1000L);
//        } catch (Exception e) {
//            LOG.error("error", e);
//        }
//
//        try {
//            Thread.sleep(1000L * 80);
//        } catch (InterruptedException e) {
//            LOG.error("error", e);
//        }

        assertTrue("test", true);
    }
}
