package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.WhoisCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
public class NettyWhoisClientHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyWhoisClientHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.WHOIS_CLIENT.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        ChannelId id = channel.id();
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("whois:{}, remoteAddress:{}, channelId:{}", message, remoteAddress, id.asLongText());
        WhoisCacheHolder.put(id, message);
    }

}
