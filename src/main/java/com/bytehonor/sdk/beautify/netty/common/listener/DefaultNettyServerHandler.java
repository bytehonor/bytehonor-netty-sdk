package com.bytehonor.sdk.beautify.netty.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

public class DefaultNettyServerHandler implements NettyServerHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNettyServerHandler.class);

    @Override
    public void onSucceed() {
        LOG.info("Server onSucceed");
    }

    @Override
    public void onFailed(Throwable error) {
        LOG.error("Server onFailed", error);
    }

    @Override
    public void onTotal(int total) {
        LOG.info("Server onTotal:{}", total);
    }

    @Override
    public void onMessage(NettyMessage message) {
        LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
    }

    @Override
    public void onDisconnected(String stamp) {
        LOG.warn("Server onDisconnected stamp:{}", stamp);
    }

    @Override
    public void onConnected(String stamp) {
        LOG.info("Server onConnected stamp:{}", stamp);
    }
}
