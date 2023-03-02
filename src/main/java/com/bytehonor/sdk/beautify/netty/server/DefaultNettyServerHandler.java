package com.bytehonor.sdk.beautify.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijianqiang
 *
 */
public class DefaultNettyServerHandler extends AbstractServerHandler {

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
    public void onDisconnected(String stamp) {
        LOG.warn("Server onDisconnected stamp:{}", stamp);
    }

    @Override
    public void onConnected(String stamp) {
        LOG.info("Server onConnected stamp:{}", stamp);
    }

//    @Override
//    public void onMessage(NettyMessage message) {
//        LOG.info("Server onMessage text:{}, stamp:{}", message.getText(), message.getStamp());
//    }
}
