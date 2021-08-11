package com.bytehonor.sdk.netty.bytehonor.common.util;

/**
 * @author lijianqiang
 *
 */
public class NettyByteUtils {

    public static String bytesToHexStrings(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(byteToHexString(bytes[i]));
        }
        return stringBuilder.toString();
    }

    public static String byteToHexString(byte by) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = by & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     * 
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * 
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    public static int check(byte[] bytes) {
        int check = 0x00;
        for (int i = 0; i < bytes.length; i++) {
            check += (int) bytes[i];
        }
        return check;
    }

    public static byte[] intToByte2(int i) {
        byte[] result = new byte[2];
        result[0] = (byte) ((i >> 8) & 0xFF);
        result[1] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] intToByte4(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int byte2ToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (1 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    public static int byte4ToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    public static void copy(byte[] src, byte[] dest, int length) {
        System.arraycopy(src, 0, dest, 0, length);
    }

    public static byte[] subBytes(byte[] src, int length) {
        byte[] dest = new byte[length];
        copy(src, dest, length);
        return dest;
    }
}
