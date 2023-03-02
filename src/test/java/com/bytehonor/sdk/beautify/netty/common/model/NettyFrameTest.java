package com.bytehonor.sdk.beautify.netty.common.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;

public class NettyFrameTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyFrameTest.class);
    
    @Test
    public void test() {
        NettyFrame ping = NettyFrame.ping();
        LOG.info("test3 ping:{}", NettyJsonUtils.toJson(ping));

        LOG.info("test3 pong:{}", NettyJsonUtils.toJson(NettyFrame.pong()));

    }

}
