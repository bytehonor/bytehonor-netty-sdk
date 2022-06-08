package com.bytehonor.sdk.beautify.netty.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyByteUtilsTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyByteUtilsTest.class);

    @Test
    public void test() {
        String hex = "00a98f1e";
        byte[] bytes = NettyByteUtils.hexStringToBytes(hex);
        String res = NettyByteUtils.bytesToHexStrings(bytes);
        LOG.info("hex:{}, res:{}", hex, res);
        assertTrue("test", hex.equals(res));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            byte b = (byte) i;
            char c = (char) i;
            LOG.info("byte:{}, char:{}, hex:{}", b, c, NettyByteUtils.byteToHexString(b));
        }
    }

}
