package com.bytehonor.sdk.netty.bytehonor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;

public class NettyClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientTest.class);

    @Test
    public void test() {
        boolean isOk = true;
        NettyClient client = new NettyClient("127.0,0,1", 9090);
        try {
            client.start();
            NettyMessageSender.send(client.getChannel(), "hello world");
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
            isOk = false;
        }

        assertTrue("test", isOk);
    }

}
