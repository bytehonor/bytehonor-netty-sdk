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
        byte[] bytes = NettyDataUtils.build(text);
        
        NettyDataUtils.validate(bytes);

        int length = bytes.length;

        int lengthData = length - 7;
        byte[] data = new byte[lengthData];
        NettyByteUtils.copy(bytes, 4, lengthData, data);

        String msg = new String(data);

        LOG.info("text:{}, bytes:{}", text, bytes);
        LOG.info("msg:{}, data:{}", msg, data);

        assertTrue("testBuildString", text.equals(msg));
    }

}
