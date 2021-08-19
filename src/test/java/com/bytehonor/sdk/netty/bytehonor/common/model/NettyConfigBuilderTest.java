package com.bytehonor.sdk.netty.bytehonor.common.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NettyConfigBuilderTest {

    @Test
    public void test() {
        NettyConfigBuilder builder = NettyConfigBuilder.make();
        NettyConfig config = builder.server(1, 6, 10, 2, 4).ssl(true, false, "123456").lengths(0, 0, 0).build();

        assertTrue("test", 1 == config.getReadIdleTimeSeconds());
    }
}
