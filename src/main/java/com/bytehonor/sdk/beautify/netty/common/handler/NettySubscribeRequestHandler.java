package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.SubjectChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeRequest;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeResponse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

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
        final ChannelId id = channel.id();
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channel:{}", message, id.asLongText());
        }

        SubscribeRequest request = NettyPayload.reflect(message, SubscribeRequest.class);
        if (request.getSubjects() == null) {
            LOG.warn("subscribe subject null");
            return;
        }

        Set<String> subjects = toSet(request.getSubjects());
        for (String subject : subjects) {
            LOG.info("subscribe:{}, subject:{}", request.getSubscribed(), subject);
            if (request.getSubscribed()) {
                SubjectChannelCacheHolder.put(subject, id);
            } else {
                SubjectChannelCacheHolder.remove(subject, id);
            }
        }

        SubscribeResponse result = SubscribeResponse.of(request.getSubjects(), subjects.size());
        NettyMessageSender.subscribeResponse(channel, result);
    }

    private Set<String> toSet(String value) {
        Set<String> set = new HashSet<String>();
        String[] arr = value.split(",");
        for (String a : arr) {
            if (a.length() < 2) {
                continue;
            }
            set.add(a);
        }
        return set;
    }
}
