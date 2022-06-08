package com.bytehonor.sdk.beautify.netty.common.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NettyConfigBuilderTest {

    @Test
    public void test() {
        NettyConfigBuilder builder = NettyConfigBuilder.server(81);
        NettyConfig config = builder.servers(2, 4).ssl(true, false, "123456").lengths(0, 0, 0).build();

        assertTrue("test", 2 == config.getBossThreads());
    }
}
