package com.bytehonor.sdk.netty.bytehonor.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerTest.class);

    @Test
    public void test() {

        new NettyServer().start(9090);

        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }

        assertTrue("test", true);
    }

}
