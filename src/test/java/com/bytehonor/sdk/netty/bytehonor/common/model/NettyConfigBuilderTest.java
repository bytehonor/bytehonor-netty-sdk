package com.bytehonor.sdk.netty.bytehonor.common.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NettyConfigBuilderTest {

    @Test
    public void test() {
        NettyConfigBuilder builder = NettyConfigBuilder.make();
        NettyConfig config = builder.idles(1, 6, 10).ssl(true, false, "123456").lengths(0, 0, 0).threads(1, 2, 1)
                .build();

        assertTrue("test", 1 == config.getReadIdleTimeSeconds());
    }
}
