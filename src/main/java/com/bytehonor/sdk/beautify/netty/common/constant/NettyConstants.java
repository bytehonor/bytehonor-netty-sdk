package com.bytehonor.sdk.beautify.netty.common.constant;

/**
 * @author lijianqiang
 *
 */
public class NettyConstants {

    public static final int CAPACITY = 40960;

    /**
     * 设置TCP缓冲区
     */
    public static final int SO_BACKLOG = 1024;

    /**
     * 接收客户端信息的最大长度
     */
    public static final int SO_RCVBUF = Integer.MAX_VALUE - 1024; // 4个字节表示长度 可以支持 Integer.MAX

    public static final byte HEAD = 0x24;
    public static final String HEAD_S = "$";

    public static final byte END = 0x26;
    public static final String END_S = "#";

    public static final int HEAD_SIZE = 1;
    public static final int END_SIZE = 1;
    // public static final int TYPE_SIZE = 1;
    // public static final int TYPE_DEFAULT = NettyTypeEnum.PUBLIC_PAYLOAD.getType();
    // public static final int LENGTH_OFFSET = 1 + TYPE_SIZE; // 头字节
    public static final int LENGTH_FIELD_OFFSET = HEAD_SIZE; // 头字节
    public static final int LENGTH_FIELD_LENGTH = 4;
    public static final int MAX_FRAME_LENGTH = SO_RCVBUF - 32; // 最大长度减去一点
    public static final int CHECK_SIZE = 4;

    /**
     * ZeroPadding, java没有ZeroPadding用NoPadding代替
     */
    public static final String AES_MODE = "AES/ECB/NoPadding";

    public static final String AES_KEY = "bytehonor_201803";

    public static final int READ_IDLE_TIMEOUT_SECONDS = 0;
    public static final int WRITE_IDLE_TIMEOUT_SECONDS = 0;
    public static final int ALL_IDLE_TIMEOUT_SECONDS = 60;

    public static final int CONNECT_TIMEOUT_MILLIS = 5000;

    public static final String SSL_PASSWORD = "bytehonor";

    public static String JKS_FILE_PATH = "cert/server.jks";
}
