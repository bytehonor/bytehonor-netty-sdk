package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeRequest;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeResult;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettySubscribeRequestHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeRequestHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.SUBSCRIBE_REQUEST.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channel:{}", message, channel.id().asLongText());
        }

        NettyPayload payload = NettyPayload.fromJson(message);
        SubscribeRequest request = payload.one(SubscribeRequest.class);

        PayloadHandler handler = PayloadHandlerFactory.get(request.getCategory());
        boolean hasHandler = handler != null;
        if (hasHandler) {
            String key = SubscribeChannelHolder.makeKey(channel.id(), request.getCategory());
            if (request.getSubscribed()) {
                SubscribeChannelHolder.put(key, channel.id());
            } else {
                SubscribeChannelHolder.remove(key);
            }
        } else {
            LOG.error("no subscribe handler! category:{}", request.getCategory());
        }

        LOG.info("subscribe:{}, category:{}, hasHandler:{}", request.getSubscribed(), request.getCategory(),
                hasHandler);
        SubscribeResult result = SubscribeResult.of(request.getCategory(), hasHandler);
        NettyMessageSender.subscribeResult(channel, result);
    }

}
