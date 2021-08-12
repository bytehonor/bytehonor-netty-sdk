package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.nio.charset.Charset;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;

import io.netty.buffer.ByteBuf;

public class NettyDataUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDataUtils.class);

    public static byte[] build(String data) {
        Objects.requireNonNull(data, "data");
        return build(NettyConstants.TYPE_DEFAULT, data.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * 
     * @param type
     * @param data
     * @return
     */
    public static byte[] build(int type, byte[] data) {
        int lengthData = data.length;
        LOG.info("type:{}, lengthData:{}", type, lengthData);
        int total = totalSizeFromData(lengthData);

        byte[] bytes = new byte[total];
        bytes[0] = NettyConstants.HEAD;
        bytes[1] = (byte) type;
        int lengthBody = 3 + lengthData; // 数据长度，2byte，包含data+check+end
        byte lenBytes[] = NettyByteUtils.intToByte2(lengthBody);
        bytes[2] = lenBytes[0];
        bytes[3] = lenBytes[1];
        for (int i = 0; i < lengthData; i++) {
            bytes[4 + i] = data[i];
        }

        byte[] checks = buildCecheckBytes(bytes);
        int checkValue = NettyByteUtils.sumBytes(checks);
        byte[] checkBytes = NettyByteUtils.intToByte2(checkValue);
        bytes[total - 3] = checkBytes[0];
        bytes[total - 2] = checkBytes[1];
        bytes[total - 1] = NettyConstants.END;
        return bytes;
    }

    public static byte[] buildCecheckBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        int total = bytes.length;
        // 校验方式：累加和，check = type + length + data
        int lengthCheck = total - 1 - NettyConstants.CHECK_SIZE - 1;
        byte[] checks = new byte[lengthCheck];
        NettyByteUtils.copy(bytes, 1, lengthCheck, checks);
        return checks;
    }

    public static int totalSizeFromData(int lengthData) {
        // 1 + 1 + 2 + length + 2 + 1
        return 1 + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + lengthData + NettyConstants.CHECK_SIZE + 1;
    }

    public static int dataSizeFromTotal(int lengthTotal) {
        // 1 + 1 + 2 + length + 2 + 1
        return lengthTotal - 1 - NettyConstants.TYPE_SIZE - NettyConstants.LENGTH_SIZE - NettyConstants.CHECK_SIZE - 1;
    }

    public static byte[] readBytes(ByteBuf buf) {
        Objects.requireNonNull(buf, "buf");
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);// 复制内容到字节数组bytes
        return bytes;
    }

    public static void validate(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        int total = bytes.length;
        if (total < 8) {
            LOG.error("bytes:({}) total length invalid", bytes);
            throw new RuntimeException("bytes total length invalid");
        }
        // 头字节校验
        if (NettyConstants.HEAD != bytes[0]) {
            LOG.error("bytes:({}) HEAD invalid", bytes);
            throw new RuntimeException("bytes HEAD invalid");
        }
        // 尾字节校验
        if (NettyConstants.END != bytes[total - 1]) {
            LOG.error("bytes:({}) HEAD invalid", bytes);
            throw new RuntimeException("bytes END invalid");
        }

        // 长度校验
        int lengthValue = parseLengthValue(bytes);
        int totalSize = 1 + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + lengthValue;
        if (total != totalSize) {
            LOG.error("bytes:({}) total:{} != totalSize:{}", bytes, total, totalSize);
            throw new RuntimeException("bytes total length not true");
        }

        // 最后check校验
        int checkValue = parseCheckValue(bytes);
        byte[] checks = buildCecheckBytes(bytes);
        int checkValue2 = NettyByteUtils.sumBytes(checks);
        if (checkValue != checkValue2) {
            LOG.error("bytes:({}) checkValue:{} != checkValue2:{}", bytes, checkValue, checkValue2);
            throw new RuntimeException("bytes check failed");
        }
    }

    public static int parseLengthValue(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        byte[] lengthBytes = new byte[NettyConstants.LENGTH_SIZE];
        NettyByteUtils.copy(bytes, NettyConstants.LENGTH_OFFSET, NettyConstants.LENGTH_SIZE, lengthBytes);
        return NettyByteUtils.byte2ToInt(lengthBytes);
    }

    public static int parseCheckValue(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        int total = bytes.length;
        byte[] checkValueBytes = new byte[NettyConstants.CHECK_SIZE];
        int checkFrom = total - 1 - NettyConstants.CHECK_SIZE;
        NettyByteUtils.copy(bytes, checkFrom, NettyConstants.CHECK_SIZE, checkValueBytes);
        return NettyByteUtils.byte2ToInt(checkValueBytes);
    }

    public static String parseData(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        int total = bytes.length;
        int lengthData = dataSizeFromTotal(total);
        byte[] data = new byte[lengthData];
        NettyByteUtils.copy(bytes, 1 + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE, lengthData, data);
        return new String(data);
    }
}
