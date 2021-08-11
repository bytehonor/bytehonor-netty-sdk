package com.bytehonor.sdk.netty.bytehonor.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.ProtocolConstants;

public class NettyAesByteUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyAesByteUtils.class);

    private static byte[] SEC_KEY_BYTES = to16BitBytes(ProtocolConstants.AES_KEY.getBytes());

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(ProtocolConstants.AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SEC_KEY_BYTES, "AES"));
            return cipher.doFinal(to16BitBytes(data));
        } catch (Exception e) {
            LOG.error("encrypt data:{}, error", data);
            return null;
        }
    }

    /**
     * 解密 hexStr 必须是16的倍数
     */
    public static byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(ProtocolConstants.AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SEC_KEY_BYTES, "AES"));
            byte[] bytes = cipher.doFinal(data);
            // String src = new String(bytes, Charset.forName("UTF-8")).trim();
            // return src.getBytes();
            return bytes;
        } catch (Exception e) {
            LOG.error("decrypt data:{} error", data, e);
            return null;
        }
    }

    private static byte[] to16BitBytes(byte[] data) {
        int len = (data.length / 16 + data.length % 16 == 0 ? 0 : 1) * 16;
        byte[] bs = new byte[len];
        System.arraycopy(data, 0, bs, 0, data.length);
        return bs;
    }
}
