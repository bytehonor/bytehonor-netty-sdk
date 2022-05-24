package com.bytehonor.sdk.netty.bytehonor.common.listener;

import io.netty.channel.Channel;

public interface ClientListener {

    public void onConnect(Channel channel);

    public void onDisconnect(String msg);

    public void onError(Throwable error);
}
