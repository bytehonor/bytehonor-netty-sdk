package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.netty.bytehonor.common.constant.ProtocolConstants;

/**
 * @author lijianqiang
 *
 */
public class LmmProtocolBuilder {
    
    // private static final Logger LOG = LoggerFactory.getLogger(LmmAesUtils.class);

    private static final String HEAD = ProtocolConstants.HEAD;

    private static final String END = ProtocolConstants.END;

    private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<String, String>(512);
    
    /**
     * 封装指令
     * 
     * @param cmd
     * @param subCmd
     * @param data
     * @return
     */
    public static String cmd(byte cmd, byte subCmd, byte[] data) {
        // byte[] dataAes = LmmAesByteUtils.encrypt(data);
        return build(cmd, subCmd, data);
    }

    /**
     * 没有对data加密
     * 
     * @param cmd
     * @param subCmd
     * @param data
     * @return
     */
    private static String build(byte cmd, byte subCmd, byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append(HEAD);
        sb.append(NettyByteUtils.byteToHexString(cmd));
        sb.append("00"); // subCmd是2个字节，补一节00
        sb.append(NettyByteUtils.byteToHexString(subCmd));
        int length = data.length;
        int lenFinal = 3 + length; // 数据长度，2byte，包含data+check+end
        byte lenBytes[] = NettyByteUtils.intToByte2(lenFinal);
        sb.append(NettyByteUtils.bytesToHexStrings(lenBytes));
        sb.append(NettyByteUtils.bytesToHexStrings(data));

        // 校验方式：累加和，Check = command + subcommand + length + data
        byte[] checks = new byte[5 + length];
        checks[0] = cmd;
        checks[1] = 0x00; // subCmd是2个字节，补一节00
        checks[2] = subCmd;
        checks[3] = lenBytes[1];
        checks[4] = lenBytes[0];
        for (int i = 0; i < length; i++) {
            checks[5 + i] = data[i];
        }
        int checkValue = NettyByteUtils.check(checks);
        byte[] chackBytes = NettyByteUtils.intToByte2(checkValue);
        sb.append(NettyByteUtils.bytesToHexStrings(chackBytes));
        sb.append(END);
        return sb.toString();
    }


    /**
     * 0x01 开机
     * 
     * @return
     */
    public static String cmd0x01() {
        // return HEAD + CMD + "00010004000006" + END;
        String value = CACHE.get("cmd0x01");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x01, new byte[] { 0x00 });
        CACHE.put("cmd0x01", value);
        return value;
    }

    /**
     * 0x02 关机
     * 
     * @return
     */
    public static String cmd0x02() {
        // return HEAD + CMD + "00020004000007" + END;
        String value = CACHE.get("cmd0x02");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x02, new byte[] { 0x00 });
        CACHE.put("cmd0x02", value);
        return value;
    }

    /**
     * 0x03 强冲
     * 
     * @return
     */
    public static String cmd0x03() {
        // return HEAD + CMD + "00030004000008" + END;
        String value = CACHE.get("cmd0x03");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x03, new byte[] { 0x00 });
        CACHE.put("cmd0x03", value);
        return value;
    }

    /**
     * 0x05 系统初始化
     * 
     * @return
     */
    public static String cmd0x05(byte[] data) {
        return cmd((byte) 0x01, (byte) 0x05, data);
    }

    /**
     * 0x06 滤芯复位
     * 
     * @return
     */
    public static String cmd0x06(byte[] data) {
        return cmd((byte) 0x01, (byte) 0x06, data);
    }

    /**
     * 0x0008 时钟同步, 精确到秒
     * 
     * @return
     */
    public static String cmd0x08(int timestamp) {
        byte[] time = NettyByteUtils.intToByte4(timestamp);
        return cmd((byte) 0x01, (byte) 0x08, time);
    }

    /**
     * 0x09 日志查询
     * 
     * @return
     */
    public static String cmd0x09() {
        // return HEAD + CMD + "0009000400000e" + END;
        String value = CACHE.get("cmd0x09");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x09, new byte[] { 0x00 });
        CACHE.put("cmd0x09", value);
        return value;
    }

    /**
     * 0x0a 恢复出厂设置（解绑）
     * 
     * @return
     */
    public static String cmd0x0a() {
        // return HEAD + CMD + "000a000400000f" + END;
        String value = CACHE.get("cmd0x0a");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x0a, new byte[] { 0x00 });
        CACHE.put("cmd0x0a", value);
        return value;
    }

    /**
     * 0x0b 用时同步
     * 
     * Data = 剩余天数(2byte) + 已用天数(2byte)
     * 
     * @return
     */
    public static String cmd0x0b(int lastDays, int useDays) {
        byte[] data = new byte[4];
        byte[] ld = NettyByteUtils.intToByte2(lastDays);
        byte[] ud = NettyByteUtils.intToByte2(useDays);
        data[0] = ld[0];
        data[1] = ld[1];
        data[2] = ud[0];
        data[3] = ud[1];
        return cmd((byte) 0x01, (byte) 0x0b, data);
    }

    /**
     * 0x0d 查询设备信息
     * 
     * @return
     */
    public static String cmd0x0d() {
        // return HEAD + CMD + "000d0004000012" + END;
        String value = CACHE.get("cmd0x0d");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x0d, new byte[] { 0x00 });
        CACHE.put("cmd0x0d", value);
        return value;
    }

    /**
     * 0x0e 获取信号和ICCID
     * 
     * @return
     */
    public static String cmd0x0e() {
        // return HEAD + CMD + "000e0004000013" + END;
        String value = CACHE.get("cmd0x0e");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x0e, new byte[] { 0x00 });
        CACHE.put("cmd0x0e", value);
        return value;
    }

    /**
     * 0x0f 机器锁定
     * 
     * @return
     */
    public static String cmd0x0f() {
        // return HEAD + CMD + "000f0004000014" + END;
        String value = CACHE.get("cmd0x0f");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x0f, new byte[] { 0x00 });
        CACHE.put("cmd0x0f", value);
        return value;
    }

    /**
     * 0x10 取水启动
     * 
     * Data = 最大限制出水量(2byte) （单位ml）
     * 
     * @return
     */
    public static String cmd0x10(int value) {
        byte[] data = NettyByteUtils.intToByte2(value);
        return cmd((byte) 0x01, (byte) 0x10, data);
    }

    /**
     * 0x11 取水解锁
     * 
     * Data = 最大限制出水量(2byte) （单位ml）
     * 
     * @return
     */
    public static String cmd0x11(int value) {
        byte[] data = NettyByteUtils.intToByte2(value);
        return cmd((byte) 0x01, (byte) 0x11, data);
    }

    /**
     * 0x12 设备自检
     * 
     * @return
     */
    public static String cmd0x12() {
        // return HEAD + CMD + "00120004000017" + END;
        String value = CACHE.get("cmd0x12");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x12, new byte[] { 0x00 });
        CACHE.put("cmd0x12", value);
        return value;
    }

    /**
     * 0x15 查询基站定位
     * 
     * @return
     */
    public static String cmd0x15() {
        // return HEAD + CMD + "0015000400001a" + END;
        String value = CACHE.get("cmd0x15");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x15, new byte[] { 0x00 });
        CACHE.put("cmd0x15", value);
        return value;
    }

    /**
     * 0x16 查询设备ID
     * 
     * @return
     */
    public static String cmd0x16() {
        // return HEAD + CMD + "0016000400001b" + END;
        String value = CACHE.get("cmd0x16");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x16, new byte[] { 0x00 });
        CACHE.put("cmd0x16", value);
        return value;
    }

    /**
     * 0x19 单次流量查询
     * 
     * @return
     */
    public static String cmd0x19() {
        // return HEAD + CMD + "0019000400001f" + END;
        String value = CACHE.get("cmd0x19");
        if (value != null) {
            return value;
        }
        value = cmd((byte) 0x01, (byte) 0x19, new byte[] { 0x00 });
        CACHE.put("cmd0x19", value);
        return value;
    }
}
