package com.bytehonor.sdk.beautify.netty.common.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

@Deprecated
public class NettyConfigBuilderTest {

    @Test
    public void test() {
        NettyConfigBuilder builder = NettyConfigBuilder.server(81);
        NettyClientConfig config = builder.ssl(true, false, "123456").clients(1, 0).build();

        assertTrue("test", 1 == config.getClientThreads());
    }
}
