package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.AppidChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
public class NettyAppidHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyAppidHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.APPID.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        ChannelId channelId = channel.id();
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channelId:{}", message, channelId.asLongText());
        }
        AppidChannelCacheHolder.put(message, channelId);
    }

}
