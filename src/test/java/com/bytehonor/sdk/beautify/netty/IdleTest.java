package com.bytehonor.sdk.beautify.netty;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyClientConfig;

public class IdleTest {

    private static final Logger LOG = LoggerFactory.getLogger(IdleTest.class);

    @Test
    public void test() {
        NettyClientConfig server = new NettyClientConfig();
       // NettyServerContanier.start(server, new DefaultNettyServerHandler());

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        NettyClientConfig client = new NettyClientConfig();
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
