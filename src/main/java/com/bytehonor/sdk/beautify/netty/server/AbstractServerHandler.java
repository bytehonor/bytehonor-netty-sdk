package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageReceiver;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractServerHandler extends NettyMessageReceiver implements NettyServerHandler {

    public AbstractServerHandler() {
        this(40960);
    }

    public AbstractServerHandler(int queues) {
        super(queues);
    }

    @Override
    public final void onMessage(String stamp, String text) {
        addMessage(stamp, text);
    }

}
