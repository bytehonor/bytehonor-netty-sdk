package com.bytehonor.sdk.netty.bytehonor.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyAesUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyByteUtilsTest.class);

    @Test
    public void test() {
        String text = "12312412";
        LOG.info("text:({}), bytes:({})", text, text.getBytes());
        String encrypt = NettyAesUtils.encrypt(text);
        LOG.info("加密后:({}), length:{}", encrypt, encrypt.length());
        String decrypt = NettyAesUtils.decrypt(encrypt);
        LOG.info("解密后:({}), equals:{}, bytes:({})", decrypt, text.equals(decrypt), decrypt.getBytes());

        assertTrue("test", text.equals(decrypt));
    }

    @Test
    public void test2() {
        String encrypt = "42e7bfa2a41690bcf61cc5efb5e6c2b5861bf749ec2a2c4b13a0515aff1f5715"; // c_ase_ECB_128_test
        String decrypt = NettyAesUtils.decrypt(encrypt);
        LOG.info("解密后:({}), bytes:({})", decrypt, decrypt.getBytes());
    }

}
