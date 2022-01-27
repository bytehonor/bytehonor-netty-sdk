package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class DefaultNettyListener implements NettyListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNettyListener.class);

    @Override
    public void onOpen(Channel channel) {
        LOG.info("onOpen");
    }

    @Override
    public void onClosed(String msg) {
        LOG.warn("onClosed msg:{}", msg);
    }

    @Override
    public void onError(Throwable error) {
        LOG.error("onError error", error);
    }

}
