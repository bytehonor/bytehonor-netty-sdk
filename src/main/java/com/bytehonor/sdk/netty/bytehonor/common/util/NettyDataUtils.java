package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.nio.charset.Charset;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.buffer.ByteBuf;

/**
 * @author lijianqiang
 *
 */
public class NettyDataUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDataUtils.class);

    private static final String UTF_8 = "UTF-8";

    public static byte[] build(String data) {
        Objects.requireNonNull(data, "data");

        return doBuild(NettyConstants.TYPE_DEFAULT, data.getBytes(Charset.forName(UTF_8)));
    }

    public static byte[] build(NettyTypeEnum type, String data) {
        Objects.requireNonNull(data, "data");

        return doBuild(type.getType(), data.getBytes(Charset.forName(UTF_8)));
    }

    /**
     * 
     * @param type
     * @param data
     * @return
     */
    private static byte[] doBuild(int type, byte[] data) {
        Objects.requireNonNull(data, "data");

        int lengthData = data.length;
        LOG.debug("type:{}, lengthData:{}", type, lengthData);
        int total = totalSizeFromData(lengthData);

        byte[] bytes = new byte[total];
        bytes[0] = NettyConstants.HEAD;
        bytes[1] = (byte) type;
        int lengthValue = lengthData + NettyConstants.CHECK_SIZE + NettyConstants.END_SIZE; // 数据长度，2byte，包含data+check+end
        byte lenBytes[] = NettyByteUtils.intToByte2(lengthValue);
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
        int lengthCheck = total - NettyConstants.HEAD_SIZE - NettyConstants.CHECK_SIZE - NettyConstants.END_SIZE;
        byte[] checks = new byte[lengthCheck];
        NettyByteUtils.copy(bytes, 1, lengthCheck, checks);
        return checks;
    }

    public static int totalSizeFromData(int lengthData) {
        // 1 + 1 + 2 + length + 2 + 1
        return NettyConstants.HEAD_SIZE + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + lengthData
                + NettyConstants.CHECK_SIZE + NettyConstants.END_SIZE;
    }

    public static int dataSizeFromTotal(int total) {
        // 1 + 1 + 2 + length + 2 + 1
        return total - NettyConstants.HEAD_SIZE - NettyConstants.TYPE_SIZE - NettyConstants.LENGTH_SIZE
                - NettyConstants.CHECK_SIZE - NettyConstants.END_SIZE;
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
        int totalSize = NettyConstants.HEAD_SIZE + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + lengthValue;
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

        byte[] copy = new byte[NettyConstants.LENGTH_SIZE];
        NettyByteUtils.copy(bytes, NettyConstants.LENGTH_OFFSET, NettyConstants.LENGTH_SIZE, copy);
        return NettyByteUtils.bytesToInt(copy);
    }

    public static int parseCheckValue(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        byte[] copy = new byte[NettyConstants.CHECK_SIZE];
        int from = total - NettyConstants.CHECK_SIZE - NettyConstants.END_SIZE;
        NettyByteUtils.copy(bytes, from, NettyConstants.CHECK_SIZE, copy);
        return NettyByteUtils.bytesToInt(copy);
    }

    public static String parseData(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        int lengthData = dataSizeFromTotal(total);
        byte[] copy = new byte[lengthData];
        int from = NettyConstants.HEAD_SIZE + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE;
        NettyByteUtils.copy(bytes, from, lengthData, copy);
        return new String(copy);
    }
}
