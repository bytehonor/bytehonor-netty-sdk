package com.bytehonor.sdk.beautify.netty.common.consumer;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

/**
 * @author lijianqiang
 *
 */
public interface NettyConsumer {

    public String subject();

    public void consume(NettyPayload payload);
}
