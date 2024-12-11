package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.core.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageReceiver;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractServerHandler implements NettyServerHandler {

    private final NettyMessageReceiver receiver;

    public AbstractServerHandler() {
        this.receiver = new NettyMessageReceiver();
    }

    @Override
    public final void onMessage(String stamp, String text) {
        receiver.addMessage(NettyMessage.of(stamp, text));
    }

    public final void addConsumer(NettyConsumer consumer) {
        this.receiver.addConsumer(consumer);
    }
}
