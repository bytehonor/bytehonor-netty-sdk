package com.bytehonor.sdk.beautify.netty.common.util;

import java.nio.charset.Charset;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

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

        return doBuild(data.getBytes(Charset.forName(UTF_8)));
    }

    /**
     * 
     * @param type
     * @param data
     * @return
     */
    private static byte[] doBuild(byte[] data) {
        Objects.requireNonNull(data, "data");

        int lengthData = data.length;
        LOG.debug("lengthData:{}", lengthData);
        int total = totalSizeFromData(lengthData);

        byte[] bytes = new byte[total];
        bytes[0] = NettyConstants.HEAD;
        int lengthValue = lengthData + NettyConstants.CHECK_SIZE + NettyConstants.END_SIZE; // 数据长度，4byte，包含data+check+end
        byte lenBytes[] = NettyByteUtils.intToByte4(lengthValue);
        bytes[1] = lenBytes[0];
        bytes[2] = lenBytes[1];
        bytes[3] = lenBytes[2];
        bytes[4] = lenBytes[3];
        for (int i = 0; i < lengthData; i++) {
            bytes[5 + i] = data[i];
        }

        byte[] checks = buildCecheckBytes(bytes);
        int checkValue = NettyByteUtils.sumBytes(checks);
        byte[] checkBytes = NettyByteUtils.intToByte4(checkValue);
        bytes[total - 5] = checkBytes[0];
        bytes[total - 4] = checkBytes[1];
        bytes[total - 3] = checkBytes[2];
        bytes[total - 2] = checkBytes[3];
        bytes[total - 1] = NettyConstants.END;
        return bytes;
    }

    public static byte[] buildCecheckBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        // 校验方式：累加和，check = length + data
        int lengthCheck = total - NettyConstants.HEAD_SIZE - NettyConstants.CHECK_SIZE - NettyConstants.END_SIZE;
        byte[] checks = new byte[lengthCheck];
        NettyByteUtils.copy(bytes, 1, lengthCheck, checks);
        return checks;
    }

    public static int totalSizeFromData(int lengthData) {
        // 1 + 4 + length + 4 + 1
        return NettyConstants.HEAD_SIZE + NettyConstants.LENGTH_FIELD_LENGTH + lengthData + NettyConstants.CHECK_SIZE
                + NettyConstants.END_SIZE;
    }

    public static int dataSizeFromTotal(int total) {
        // 1 + 4 + length + 4 + 1
        return total - NettyConstants.HEAD_SIZE - NettyConstants.LENGTH_FIELD_LENGTH - NettyConstants.CHECK_SIZE
                - NettyConstants.END_SIZE;
    }

    public static byte[] readBytes(ByteBuf buf) {
        Objects.requireNonNull(buf, "buf");

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);// 复制内容到字节数组bytes
        buf.release();
        return bytes;
    }

    public static void validate(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        if (total < 8) {
            LOG.error("bytes:({}) total length invalid", total);
            throw new RuntimeException("bytes total length invalid");
        }
        // 头字节校验
        if (NettyConstants.HEAD != bytes[0]) {
            LOG.error("bytes:({}) HEAD invalid", bytes[0]);
            throw new RuntimeException("bytes HEAD invalid");
        }
        // 尾字节校验
        if (NettyConstants.END != bytes[total - 1]) {
            LOG.error("bytes:({}) END invalid", bytes[total - 1]);
            throw new RuntimeException("bytes END invalid");
        }

        // 长度校验
        int lengthValue = parseLengthValue(bytes);
        int totalSize = NettyConstants.HEAD_SIZE + NettyConstants.LENGTH_FIELD_LENGTH + lengthValue;
        if (total != totalSize) {
            LOG.error("total:{} != totalSize:{}", total, totalSize);
            throw new RuntimeException("bytes total length not true");
        }

        // 最后check校验
        byte[] checks1 = parseCheckBytes(bytes);
        int checkValue1 = NettyByteUtils.bytesToInt(checks1);
        byte[] checks2 = buildCecheckBytes(bytes);
        int checkValue2 = NettyByteUtils.sumBytes(checks2);
        if (checkValue1 != checkValue2) {
            LOG.error("checks1:{}, checkValue1:{} != checkValue2:{}, bytes2:{}", checks1, checkValue1, checkValue2,
                    NettyByteUtils.intToByte2(checkValue2));
            throw new RuntimeException("bytes check failed");
        }
    }

    public static byte[] parseLengthBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        byte[] copy = new byte[NettyConstants.LENGTH_FIELD_LENGTH];
        NettyByteUtils.copy(bytes, NettyConstants.LENGTH_FIELD_OFFSET, NettyConstants.LENGTH_FIELD_LENGTH, copy);
        return copy;
    }

    public static int parseLengthValue(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        byte[] copy = parseLengthBytes(bytes);
        return NettyByteUtils.bytesToInt(copy);
    }

    public static byte[] parseCheckBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        byte[] copy = new byte[NettyConstants.CHECK_SIZE];
        int from = total - NettyConstants.CHECK_SIZE - NettyConstants.END_SIZE;
        NettyByteUtils.copy(bytes, from, NettyConstants.CHECK_SIZE, copy);
        return copy;
    }

    public static byte[] parseDataBytes(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        int total = bytes.length;
        int lengthData = dataSizeFromTotal(total);
        byte[] copy = new byte[lengthData];
        int from = NettyConstants.HEAD_SIZE + NettyConstants.LENGTH_FIELD_LENGTH;
        NettyByteUtils.copy(bytes, from, lengthData, copy);
        return copy;
    }

    public static String parseData(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");

        byte[] copy = parseDataBytes(bytes);
        return new String(copy);
    }
}
