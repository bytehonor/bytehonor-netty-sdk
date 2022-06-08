package com.bytehonor.sdk.beautify.netty.common.exception;

/**
 * @author lijianqiang
 *
 */
public class NettyBeautifyException extends RuntimeException {

    private static final long serialVersionUID = -2547149717647211360L;

    public NettyBeautifyException() {
        super();
    }

    public NettyBeautifyException(String message) {
        super(message);
    }

    public NettyBeautifyException(Exception cause) {
        super(cause);
    }
}
