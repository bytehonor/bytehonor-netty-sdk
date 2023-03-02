package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNettyClientHandler extends AbstractClientHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNettyClientHandler.class);

    @Override
    public void onOpen(String stamp) {
        LOG.info("Client onOpen stamp:{}", stamp);
    }

    @Override
    public void onClosed(String stamp, String msg) {
        LOG.warn("Client onClosed stamp:{}, msg:{}", stamp, msg);
    }

    @Override
    public void onError(String stamp, Throwable error) {
        LOG.error("Client onError error", error);
    }

//    @Override
//    public void onMessage(NettyMessage message) {
//        LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
//    }

}
