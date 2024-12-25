package com.bytehonor.sdk.beautify.netty.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bytehonor.sdk.beautify.netty.common.task.NettySleeper;

public class NettyServerTest {

    // private static final Logger LOG =
    // LoggerFactory.getLogger(NettyServerTest.class);

    @Test
    public void test() {

        NettyServer.builder(85).build().start();

        NettySleeper.sleep(15000L);

        assertTrue("test", true);
    }

}
