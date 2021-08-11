package com.bytehonor.sdk.netty.bytehonor.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyAesByteUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyByteUtilsTest.class);

    @Test
    public void test() {
        String text = "12312412";
        LOG.info("text:({}), bytes:({})", text, text.getBytes());
        byte[] encryptBytes = NettyAesByteUtils.encrypt(text.getBytes());
        String encrypt = NettyByteUtils.bytesToHexStrings(encryptBytes);
        LOG.info("加密后:({}), ({}), length:{}", encrypt, encryptBytes, encryptBytes.length);
        byte[] decryptBytes = NettyAesByteUtils.decrypt(encryptBytes);
        String decrypt = new String(decryptBytes);
        boolean equals = text.equals(decrypt);
        LOG.info("解密后:({}), bytes:({}), equals:{}", decrypt, decryptBytes, equals);

        assertTrue("test", equals);
    }

}
