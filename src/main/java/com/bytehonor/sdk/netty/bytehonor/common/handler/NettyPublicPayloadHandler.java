package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;

import io.netty.channel.Channel;

public class NettyPublicPayloadHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPublicPayloadHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.PUBLIC_PAYLOAD.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        NettyPayload payload = NettyPayload.fromJson(message);
        if (LOG.isDebugEnabled()) {
            LOG.debug("subject:{}, body:{}", payload.getSubject(), payload.getBody());
        }

        PayloadHandler handler = PayloadHandlerFactory.get(payload.getSubject());
        if (handler == null) {
            LOG.warn("no PayloadHandler! subject:{}, channel:{}", payload.getSubject(), channel.id().asLongText());
            return;
        }

        try {
            handler.handle(payload);
        } catch (Exception e) {
            String whois = ChannelCacheManager.getWhois(channel.id());
            LOG.error("whois:{}, subject:{}, handler:{}, error", whois, payload.getSubject(),
                    handler.getClass().getSimpleName(), e);
        }
    }

}
