package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractClientHandler extends NettyMessageHandler implements NettyClientHandler {

    public AbstractClientHandler() {
        this(20480);
    }

    public AbstractClientHandler(int queues) {
        super(queues);
    }

    @Override
    public final void onMessage(NettyMessage message) {
        addMessage(message);
    }
}
