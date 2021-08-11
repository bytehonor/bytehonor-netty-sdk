package com.bytehonor.sdk.netty.bytehonor.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.ProtocolConstants;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyContent;

public class NettyDataUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettySslUtils.class);

    public static NettyContent parseFromClient(String msg) {
        checkProtocol(msg);

        NettyDataUtils.checkProtocol(msg);
        String cmd = finCmd(msg);
        String subCmd = finSubCmd(msg);
        String data = findData(msg);

        byte[] bytes = NettyByteUtils.hexStringToBytes(data);
        byte[] deviceIdBytes = NettyByteUtils.subBytes(bytes, 4);
        String deviceId = NettyDataUtils.parseDevicdIdFromBytes4(deviceIdBytes);

        return new NettyContent(cmd, subCmd, data, deviceId);
    }

    public static NettyContent parseFromServer(String msg) {
        checkProtocol(msg);

        NettyDataUtils.checkProtocol(msg);
        String cmd = finCmd(msg);
        String subCmd = finSubCmd(msg);
        String data = findData(msg);

        return new NettyContent(cmd, subCmd, data, null);
    }

    public static String decryptData(String data) {
        byte[] decryptBytes = NettyAesByteUtils.decrypt(NettyByteUtils.hexStringToBytes(data));
        return NettyByteUtils.bytesToHexStrings(decryptBytes);
    }

    public static String decryptData(String data, int byteLength) {
        byte[] decryptBytes = NettyAesByteUtils.decrypt(NettyByteUtils.hexStringToBytes(data));
        byte[] copy = new byte[byteLength];
        NettyByteUtils.copy(decryptBytes, copy, byteLength);
        return NettyByteUtils.bytesToHexStrings(copy);
    }

    public static String finCmd(String msg) {
        return msg.substring(2, 4);
    }

    public static String finSubCmd(String msg) {
        return msg.substring(4, 8);
    }

    public static void checkProtocol(String msg) {
        if (msg == null) {
            throw new RuntimeException("protocol msg empty");
        }

        if (msg.startsWith(ProtocolConstants.HEAD) == false || msg.endsWith(ProtocolConstants.END) == false) {
            LOG.error("text:{}, not validate protocol content", msg);
            throw new RuntimeException("invalidate protocol msg");
        }
    }

    public static String parseDevicdId(String data) {
        byte[] bytes = NettyByteUtils.hexStringToBytes(data);
        return parseDevicdIdFromBytes4(bytes);
    }

    public static String parseDevicdIdFromBytes4(byte[] bytes) {
        int deviceId = NettyByteUtils.byte4ToInt(bytes);
        return String.valueOf(deviceId);
    }

    public static String findData(String msg) {
        return msg.substring(12, msg.length() - 6);
    }
}
