package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageReceiver;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractClientHandler extends NettyMessageReceiver implements NettyClientHandler {

    public AbstractClientHandler() {
        super();
    }

    public AbstractClientHandler(int queues) {
        super(queues);
    }

    @Override
    public final void onMessage(String stamp, String text) {
        addMessage(NettyMessage.of(stamp, text));
    }
}
