package com.bytehonor.sdk.beautify.netty.common.util;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

/**
 * @author lijianqiang
 *
 */
public class NettyAesUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyAesUtils.class);
    
    /**
     * 加密
     */
    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(NettyConstants.AES_MODE);
            LOG.info("getBlockSize:{}", cipher.getBlockSize());
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(to16BitBytes(NettyConstants.AES_KEY), "AES"));
            byte[] bytes = cipher.doFinal(to16BitBytes(text));
            return NettyByteUtils.bytesToHexStrings(bytes);
        } catch (Exception e) {
            LOG.error("encrypt str:{}, error", text);
            return null;
        }
    }

    /**
     * 解密 hexStr 必须是16的倍数
     */
    public static String decrypt(String hexStr) {
        try {
            Cipher cipher = Cipher.getInstance(NettyConstants.AES_MODE);
            LOG.info("getBlockSize:{}", cipher.getBlockSize());
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(to16BitBytes(NettyConstants.AES_KEY), "AES"));
            byte[] bytes = cipher.doFinal(NettyByteUtils.hexStringToBytes(hexStr));
            return new String(bytes, Charset.forName("UTF-8")).trim();
        } catch (Exception e) {
            LOG.error("decrypt hexStr:{} error", hexStr, e);
            return null;
        }
    }

    private static byte[] to16BitBytes(String str) {
        int len = (str.length() / 16 + str.length() % 16 == 0 ? 0 : 1) * 16;
        byte[] bs = new byte[len];
        System.arraycopy(str.getBytes(), 0, bs, 0, str.length());
        return bs;
    }
}
