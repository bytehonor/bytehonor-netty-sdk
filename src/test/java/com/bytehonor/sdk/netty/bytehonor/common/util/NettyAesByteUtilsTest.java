package com.bytehonor.sdk.netty.bytehonor.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyAesByteUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyByteUtilsTest.class);

//  @Test
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

    @Test
    public void test2() {
        byte[] bytes = new byte[] { 0x00 };
        String text = NettyByteUtils.bytesToHexStrings(bytes); // f403cd92103e91773d076b72e3b4589c
        LOG.info("bytes:{}, text:{}", bytes, text);
        byte[] encryptBytes = NettyAesByteUtils.encrypt(bytes);
        String encrypt = NettyByteUtils.bytesToHexStrings(encryptBytes);
        LOG.info("加密后:({}), ({}), length:{}", encrypt, encryptBytes, encryptBytes.length);
        byte[] decryptBytes = NettyAesByteUtils.decrypt(encryptBytes);
        String decrypt = new String(decryptBytes);
        String hex = NettyByteUtils.bytesToHexStrings(decryptBytes);
        boolean equals = text.equals(decrypt);
        LOG.info("解密后:({}), bytes:({}), equals:{}, hex:{}", decrypt, decryptBytes, equals, hex);

        String data = NettyDataUtils.decryptData(encrypt, 1);
        LOG.info("data:({}), bytes:({}), equals:{}", data, NettyByteUtils.hexStringToBytes(data), text.equals(data));
        assertTrue("test", text.equals(data));
    }

}
