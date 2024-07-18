package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageReceiver;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractServerHandler extends NettyMessageReceiver implements NettyServerHandler {

    public AbstractServerHandler() {
        super();
    }

    @Override
    public final void onMessage(String stamp, String text) {
        addMessage(NettyMessage.of(stamp, text));
    }

}
