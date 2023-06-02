package com.bytehonor.sdk.beautify.netty.common.consumer;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

/**
 * @author lijianqiang
 *
 * @param <T>
 */
public abstract class AbstractSimpleNettyConsumer<T> implements NettyConsumer {

    @Override
    public final String subject() {
        return target().getName();
    }

    @Override
    public final void consume(String stamp, NettyPayload payload) {
        process(stamp, payload.reflect(target()));
    }

    public abstract Class<T> target();

    public abstract void process(String stamp, T target);
}
