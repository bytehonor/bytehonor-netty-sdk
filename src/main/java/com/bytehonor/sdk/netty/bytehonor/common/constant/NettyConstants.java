package com.bytehonor.sdk.netty.bytehonor.common.constant;

public class NettyConstants {

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
