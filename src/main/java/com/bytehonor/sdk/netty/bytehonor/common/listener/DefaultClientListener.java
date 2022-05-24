package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class DefaultClientListener implements ClientListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultClientListener.class);

    @Override
    public void onConnect(Channel channel) {
        LOG.info("Client onOpen");
    }

    @Override
    public void onDisconnect(String msg) {
        LOG.warn("Client onClosed msg:{}", msg);
    }

    @Override
    public void onError(Throwable error) {
        LOG.error("Client onError error", error);
    }

}
