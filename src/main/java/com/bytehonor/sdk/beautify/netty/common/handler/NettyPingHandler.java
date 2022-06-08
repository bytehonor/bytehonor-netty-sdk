package com.bytehonor.sdk.beautify.netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyPingHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPingHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.PING.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channel:{}", message, channel.id().asLongText());
        }
        NettyMessageSender.pong(channel);
    }

}
