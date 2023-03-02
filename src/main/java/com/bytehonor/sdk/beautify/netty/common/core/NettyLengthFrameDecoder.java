package com.bytehonor.sdk.beautify.netty.common.core;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author lijianqiang
 *
 */
public class NettyLengthFrameDecoder extends LengthFieldBasedFrameDecoder {

    public NettyLengthFrameDecoder() {
        this(NettyConstants.MAX_FRAME_LENGTH, NettyConstants.LENGTH_FIELD_OFFSET, NettyConstants.LENGTH_FIELD_LENGTH, 0,
                0);
    }

    public NettyLengthFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
