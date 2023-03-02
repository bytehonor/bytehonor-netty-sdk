package com.bytehonor.sdk.beautify.netty;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.client.NettyClientContanier;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.DefaultNettyServerHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyClientConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.server.NettyServerContanier;

import io.netty.channel.Channel;

public class SdkTester {

    private static final Logger LOG = LoggerFactory.getLogger(SdkTester.class);

    @Test
    public void test() {
        NettyClientConfig server = new NettyClientConfig();
        //NettyServerContanier.start(server, new DefaultNettyServerHandler());

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
//                    LOG.info("onConnect");
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
//                    LOG.warn("onDisconnect:{}", msg);
//                }
//
//                @Override
//                public void onMessage(NettyMessage of) {
//                    // TODO Auto-generated method stub
//                    
//                }
//            });
//            Thread.sleep(1000L);
//        } catch (Exception e) {
//            LOG.error("error", e);
//        }

        try {
            Thread.sleep(1000L * 60);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }
}
