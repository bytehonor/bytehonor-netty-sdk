package com.bytehonor.sdk.beautify.netty.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class DefaultNettyClientListener implements NettyClientListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNettyClientListener.class);

    @Override
    public void onOpen(Channel channel) {
        LOG.info("Client onOpen");
    }

    @Override
    public void onClosed(String msg) {
        LOG.warn("Client onClosed msg:{}", msg);
    }

    @Override
    public void onError(Throwable error) {
        LOG.error("Client onError error", error);
    }

}
