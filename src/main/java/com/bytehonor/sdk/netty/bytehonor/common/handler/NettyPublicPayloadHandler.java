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

        String whois = ChannelCacheManager.getWhois(channel.id());
        PayloadHandler handler = PayloadHandlerFactory.get(payload.getSubject());
        if (handler == null) {
            LOG.warn("no handler! whois:{}, subject:{}", whois, payload.getSubject());
            return;
        }

        try {
            handler.handle(payload);
        } catch (Exception e) {
            LOG.error("whois:{}, subject:{}, handler:{}, error", whois, payload.getSubject(),
                    handler.getClass().getSimpleName(), e);
        }
    }

}
