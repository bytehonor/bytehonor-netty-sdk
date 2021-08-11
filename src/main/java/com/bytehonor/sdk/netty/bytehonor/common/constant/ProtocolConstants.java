package com.bytehonor.sdk.netty.bytehonor.common.constant;

/**
 * @author lijianqiang
 *
 */
public class ProtocolConstants {

    public static final String HEAD = "7f";

    public static final String END = "7e";

    public static final byte BYTE_CMD_SEND = (byte) 0x01;
    public static final String CMD_SEND = CommandEnum.OX01.getKey();

    public static final byte BYTE_CMD_RESPONSE = (byte) 0x02;
    public static final String CMD_RESPONSE = CommandEnum.OX02.getKey();

    public static final byte BYTE_CMD_REPORT = (byte) 0x03;
    public static final String CMD_REPORT = CommandEnum.OX03.getKey();

    public static final byte BYTE_CMD_ERROR = (byte) 0xff;
    public static final String CMD_ERROR = CommandEnum.OXFF.getKey();

    /**
     * ZeroPadding, java没有ZeroPadding用NoPadding代替
     */
    public static final String AES_MODE = "AES/ECB/NoPadding";

    public static final String AES_KEY = "bytehonor_201803";
}
