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
            LOG.debug("name:{}, json:{}", payload.getName(), payload.getJson());
        }

        PayloadHandler handler = PayloadHandlerFactory.get(payload.getName());
        if (handler == null) {
            LOG.warn("no handler! name:{}", payload.getName());
            return;
        }

        try {
            handler.handle(payload);
        } catch (Exception e) {
            LOG.error("handle payload:{} handler:{}, error", payload.getName(), handler.getClass().getSimpleName(), e);
        }
    }

}
