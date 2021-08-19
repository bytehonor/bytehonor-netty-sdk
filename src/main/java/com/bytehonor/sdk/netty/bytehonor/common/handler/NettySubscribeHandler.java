package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettySubscribeHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.SUBSCRIBE.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isInfoEnabled()) {
            LOG.info("message:{}, channel:{}", message, channel.id().asLongText());
        }

        String key = SubscribeChannelHolder.makeKey(channel.id(), message);
        SubscribeChannelHolder.put(key, channel.id());
        NettyMessageSender.accept(channel, true);
    }

}
