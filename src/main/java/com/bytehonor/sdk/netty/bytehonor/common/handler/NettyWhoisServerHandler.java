package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyWhoisServerHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyWhoisServerHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.WHOIS_SERVER.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("whois:{}, remoteAddress:{}, channel:{}", message, remoteAddress, channel.id().asLongText());
        ChannelCacheManager.put(message, channel);
    }

}
