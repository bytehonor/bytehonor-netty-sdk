package com.bytehonor.sdk.netty.bytehonor.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyDataUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDataUtilsTest.class);

    @Test
    public void testBuildString() {
        String text = "hello world";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12000; i++) {
            sb.append(text).append(",");
        }
        String full = sb.toString();
        byte[] bytes = NettyDataUtils.build(full);
        LOG.info("total:{}", bytes.length);

        byte[] lengths = NettyDataUtils.parseLengthBytes(bytes);
        int lengthValue = NettyByteUtils.bytesToInt(lengths);
        LOG.info("lengths:{}, {}", lengths, lengthValue);

        byte[] checks = NettyDataUtils.parseCheckBytes(bytes);
        LOG.info("checks:{}, value:{}", checks, NettyByteUtils.bytesToInt(checks));

        NettyDataUtils.validate(bytes);

        byte[] data = NettyDataUtils.parseDataBytes(bytes);

        String msg = new String(data);

//        LOG.info("text:{}, bytes:{}", text, bytes);
//        LOG.info("msg:{}, data:{}", msg, data);

        assertTrue("testBuildString", full.equals(msg));
    }

}
