package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.HashSet;
import java.util.Set;

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

        Set<String> names = toSet(request.getNames());
        for (String name : names) {
            LOG.info("subscribe:{}, name:{}", request.getSubscribed(), name);
            String key = SubscribeChannelHolder.makeKey(channel.id(), name);
            if (request.getSubscribed()) {
                SubscribeChannelHolder.put(key, channel.id());
            } else {
                SubscribeChannelHolder.remove(key);
            }
        }

        SubscribeResult result = SubscribeResult.of(request.getNames(), names.size());
        NettyMessageSender.subscribeResult(channel, result);
    }

    private Set<String> toSet(String value) {
        Set<String> set = new HashSet<String>();

        return set;
    }
}
