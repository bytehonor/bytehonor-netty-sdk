package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyAcceptHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyAcceptHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.ACCEPT.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isInfoEnabled()) {
            LOG.info("message:{}, channel:{}", message, channel.id().asLongText());
        }
    }

}
