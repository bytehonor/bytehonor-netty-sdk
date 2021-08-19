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
public class NettyUnsubscribeHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyUnsubscribeHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.UNSUBSCRIBE.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isInfoEnabled()) {
            LOG.info("message:{}, channel:{}", message, channel.id().asLongText());
        }

        String key = SubscribeChannelHolder.makeKey(channel.id(), message);
        SubscribeChannelHolder.remove(key);
        NettyMessageSender.accept(channel, true);
    }

}
