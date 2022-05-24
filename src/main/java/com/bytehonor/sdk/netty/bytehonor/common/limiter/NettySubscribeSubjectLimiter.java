package com.bytehonor.sdk.netty.bytehonor.common.limiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubjectChannelCacheHolder;

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

    public static void limitAll() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LIST size:{}", LIST.size());
        }
        for (SubjectLimiter limiter : LIST) {
            doLimit(limiter.getSubject(), limiter.getLimit());
        }
    }

    public static void limit(String subject, int limit) {
        doLimit(subject, limit);
    }

    public static void limit(SubjectLimiter limiter) {
        Objects.requireNonNull(limiter, "limiter");
        doLimit(limiter.getSubject(), limiter.getLimit());
    }

    private static void doLimit(String subject, int limit) {
        Objects.requireNonNull(subject, "subject");
        List<ChannelId> channels = new ArrayList<ChannelId>(SubjectChannelCacheHolder.get(subject));
        int size = channels.size();
        for (int i = 0; i < limit; i++) {
            size = channels.size();
            if (size <= limit || size < 1) {
                return;
            }
            LOG.warn("subject:{}, limit:{}, size:{} overlimit.", subject, limit, size);
            try {
                ChannelId id = channels.get(size - 1); // 踢掉最后一个
                Channel last = ChannelCacheManager.getChannel(id);
                if (last != null) {
                    last.close();
                }
                SubjectChannelCacheHolder.remove(subject, id);
            } catch (Exception e) {
                LOG.error("error", e);
            }
        }
    }
}
