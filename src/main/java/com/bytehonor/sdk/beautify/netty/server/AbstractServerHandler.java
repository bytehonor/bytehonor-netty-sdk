package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractServerHandler extends NettyMessageHandler implements NettyServerHandler {

    public AbstractServerHandler() {
        this(40960);
    }

    public AbstractServerHandler(int queues) {
        super(queues);
    }

    @Override
    public final void onMessage(NettyMessage message) {
        addMessage(message);
    }

}
