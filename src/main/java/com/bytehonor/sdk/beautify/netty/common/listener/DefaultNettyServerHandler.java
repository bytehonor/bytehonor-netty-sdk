package com.bytehonor.sdk.beautify.netty.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;

import io.netty.channel.Channel;

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
    public void onDisconnected(Channel channel) {

    }

    @Override
    public void onConnected(Channel channel) {

    }
}
