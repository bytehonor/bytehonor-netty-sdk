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

        // 校验方式：累加和，check = type + length + data
        int lengthCheck = NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + lengthData;
        byte[] checks = new byte[lengthCheck];
        NettyByteUtils.copy(bytes, 1, lengthCheck, checks);
        int checkValue = NettyByteUtils.check(checks);
        byte[] checkBytes = NettyByteUtils.intToByte2(checkValue);
        bytes[total - 3] = checkBytes[0];
        bytes[total - 2] = checkBytes[1];
        bytes[total - 1] = NettyConstants.END;
        return bytes;
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
        byte[] lengthBytes = new byte[NettyConstants.LENGTH_SIZE];
        NettyByteUtils.copy(bytes, NettyConstants.LENGTH_OFFSET, NettyConstants.LENGTH_SIZE, lengthBytes);
        int length = NettyByteUtils.byte2ToInt(lengthBytes);

        // 长度校验
        int totalSize = 1 + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE + length;
        if (total != totalSize) {
            LOG.error("bytes:({}) total:{} != totalSize:{}", bytes, total, totalSize);
            throw new RuntimeException("bytes total length not true");
        }

        // 最后check校验
        // byte[] checkBytes = new byte[NettyConstants.CHECK_SIZE];
    }

    public static String parseData(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes");
        int lengthTotal = bytes.length;
        int lengthData = dataSizeFromTotal(lengthTotal);
        byte[] data = new byte[lengthData];
        NettyByteUtils.copy(bytes, 1 + NettyConstants.TYPE_SIZE + NettyConstants.LENGTH_SIZE, lengthData, data);
        return new String(data);
    }
}
