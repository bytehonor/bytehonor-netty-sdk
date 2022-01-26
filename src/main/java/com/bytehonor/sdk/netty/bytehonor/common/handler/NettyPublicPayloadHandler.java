package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            LOG.debug("whois:{}, subject:{}, body:{}", payload.getWhois(), payload.getSubject(), payload.getBody());
        }

        PayloadHandler handler = PayloadHandlerFactory.get(payload.getSubject());
        if (handler == null) {
            LOG.warn("no handler! whois:{}, subject:{}", payload.getWhois(), payload.getSubject());
            return;
        }

        try {
            handler.handle(payload);
        } catch (Exception e) {
            LOG.error("whois:{}, subject:{}, handler:{}, error", payload.getWhois(), payload.getSubject(),
                    handler.getClass().getSimpleName(), e);
        }
    }

}
