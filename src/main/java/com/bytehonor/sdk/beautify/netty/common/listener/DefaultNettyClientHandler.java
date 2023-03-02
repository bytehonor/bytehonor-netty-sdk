package com.bytehonor.sdk.beautify.netty.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class DefaultNettyClientHandler extends AbstractClientHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNettyClientHandler.class);

    @Override
    public void onOpen(String stamp) {
        LOG.info("Client onOpen stamp:{}", stamp);
    }

    @Override
    public void onClosed(String msg) {
        LOG.warn("Client onClosed msg:{}", msg);
    }

    @Override
    public void onError(String stamp, Throwable error) {
        LOG.error("Client onError error", error);
    }

//    @Override
//    public void onMessage(NettyMessage message) {
//        LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
//    }

    @Override
    public void onPorcess(String stamp, NettyPayload payload) {
        LOG.info("Client onPorcess subject:{}, body:{}, stamp:{}", payload.getSubject(), payload.getBody(), stamp);
    }

}
