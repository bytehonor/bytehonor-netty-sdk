package com.bytehonor.sdk.netty.bytehonor.server;

import org.junit.Test;

public class NettyServerTest {

    @Test
    public void test() {

        NettyServer.start(9090);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
