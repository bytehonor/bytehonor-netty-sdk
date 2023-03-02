package com.bytehonor.sdk.beautify.netty.common.core;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyIdleStateChecker extends IdleStateHandler {

    public NettyIdleStateChecker() {
        this(NettyConstants.READ_IDLE_TIMEOUT_SECONDS, NettyConstants.WRITE_IDLE_TIMEOUT_SECONDS,
                NettyConstants.ALL_IDLE_TIMEOUT_SECONDS);
    }

    public NettyIdleStateChecker(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

}
