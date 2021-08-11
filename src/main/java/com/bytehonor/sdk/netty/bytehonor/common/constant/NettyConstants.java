package com.bytehonor.sdk.netty.bytehonor.common.constant;

public class NettyConstants {

    public static final byte HEAD = 0x24;
    public static final String HEAD_S = "$";

    public static final byte END = 0x26;
    public static final String END_S = "#";

    public static final int TYPE_SIZE = 1;
    public static final int TYPE_DEFAULT = NettyTypeEnum.NORMAL.getType();
    public static final int LENGTH_OFFSET = 1 + TYPE_SIZE; // 头字节
    public static final int LENGTH_SIZE = 2;
    public static final int MAX_LENGTH = 1024 * 1024;
    public static final int CHECK_SIZE = 2;

    /**
     * ZeroPadding, java没有ZeroPadding用NoPadding代替
     */
    public static final String AES_MODE = "AES/ECB/NoPadding";

    public static final String AES_KEY = "bytehonor_201803";

    public static final int CLIENT_HEART_TIMEOUT_SECONDS = 310;

    /**
     * 设置TCP缓冲区
     */
    public static final int SO_BACKLOG = 1024;

    /**
     * 接收客户端信息的最大长度
     */
    public static final int SO_RCVBUF = 2 * 1024;
}
