package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.core.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageReceiver;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractClientHandler implements NettyClientHandler {

    private NettyMessageReceiver receiver;

    public AbstractClientHandler() {
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
