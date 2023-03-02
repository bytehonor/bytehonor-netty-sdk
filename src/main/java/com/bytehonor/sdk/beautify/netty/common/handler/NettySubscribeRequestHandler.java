package com.bytehonor.sdk.beautify.netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyTypeEnum;

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
//        final ChannelId id = channel.id();
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("message:{}, channel:{}", message, id.asLongText());
//        }
//
//        SubscribeRequest request = null;//NettyPayload.reflect(message, SubscribeRequest.class);
//        if (request.getSubject() == null) {
//            LOG.warn("subscribe subject null");
//            return;
//        }
//
//        LOG.info("subscribe:{}, subject:{}", request.getSubscribed(), request.getSubject());
//        if (request.getSubscribed()) {
//            SubjectChannelCacheHolder.put(request.getSubject(), id);
//        } else {
//            SubjectChannelCacheHolder.remove(request.getSubject(), id);
//        }
//
//        SubscribeResponse result = SubscribeResponse.of(request.getSubject(), 1);
//        NettyMessageSender.subscribeResponse(channel, result);
    }

}
