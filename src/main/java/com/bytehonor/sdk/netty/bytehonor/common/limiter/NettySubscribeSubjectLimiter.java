package com.bytehonor.sdk.netty.bytehonor.common.limiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubscribeCacheHolder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class NettySubscribeSubjectLimiter {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeSubjectLimiter.class);

    private static final List<SubjectLimiter> LIST = new ArrayList<SubjectLimiter>();

    public static void add(SubjectLimiter limiter) {
        Objects.requireNonNull(limiter, "limiter");
        Objects.requireNonNull(limiter.getSubject(), "subject");
        LIST.add(limiter);
    }

    public static void limit() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LIST size:{}", LIST.size());
        }
        for (SubjectLimiter limiter : LIST) {
            doLimit(limiter);
        }
    }

    private static void doLimit(SubjectLimiter limiter) {
        Objects.requireNonNull(limiter, "limiter");
        String subject = limiter.getSubject();
        List<ChannelId> channels = SubscribeCacheHolder.get(subject);
        int size = channels.size();
        if (size < 1) {
            return;
        }
        if (size > limiter.getLimit()) {
            LOG.warn("subject:{}, limit:{}, size:{} overlimit.", subject, limiter.getLimit(), size);
            ChannelId id = channels.get(size - 1); // 踢掉最后一个
            Channel last = ChannelCacheHolder.get(id);
            if (last != null) {
                last.close();
            }
            SubscribeCacheHolder.remove(subject, id);
        }
    }
}
