package com.bytehonor.sdk.netty.bytehonor.common.exception;

/**
 * @author lijianqiang
 *
 */
public class BytehonorNettySdkException extends RuntimeException {

    private static final long serialVersionUID = -2547149717647211360L;

    public BytehonorNettySdkException() {
        super();
    }

    public BytehonorNettySdkException(String message) {
        super(message);
    }

    public BytehonorNettySdkException(Exception cause) {
        super(cause);
    }
}
