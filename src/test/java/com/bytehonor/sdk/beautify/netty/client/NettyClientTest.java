package com.bytehonor.sdk.beautify.netty.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;

public class NettyClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientTest.class);

    @Test
    public void test() {
        boolean isOk = true;
        NettyClient client = new NettyClient("127.0.0.1", 85, new NettyClientHandler() {

            @Override
            public void onOpen(String stamp) {
                LOG.info("onOpen");
            }

            @Override
            public void onClosed(String stamp, String msg) {
                LOG.warn("onClosed stamp:{}", stamp, msg);
            }

            @Override
            public void onError(String stamp, Throwable error) {
                LOG.error("onError", error);
            }

            @Override
            public void onMessage(NettyMessage message) {
                
            }

        });
        try {
            client.run();
            NettySleeper.sleep(15000L);
        } catch (Exception e) {
            LOG.error("error", e);
            isOk = false;
        }

        assertTrue("test", isOk);
    }

}
