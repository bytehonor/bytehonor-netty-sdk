package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyHeartHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyHeartHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.HEART.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("heart:{}, channel:{}", message, channel.id().asLongText());
        }
    }

}
