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

    private static final List<SubjectLimitation> LIST = new ArrayList<SubjectLimitation>();

    public static void add(SubjectLimitation limitation) {
        Objects.requireNonNull(limitation, "limitation");
        Objects.requireNonNull(limitation.getSubject(), "subject");
        LIST.add(limitation);
    }

    public static void process() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LIMITATIONS size:{}", LIST.size());
        }
        for (SubjectLimitation limitation : LIST) {
            doProcess(limitation);
        }
    }

    private static void doProcess(SubjectLimitation limitation) {
        Objects.requireNonNull(limitation, "limitation");
        String subject = limitation.getSubject();
        List<ChannelId> channels = SubscribeCacheHolder.get(subject);
        int size = channels.size();
        if (size < 1) {
            return;
        }
        if (size > limitation.getLimit()) {
            LOG.warn("subject:{}, limit:{}, size:{} overlimit.", subject, limitation.getLimit(), size);
            ChannelId id = channels.get(size - 1); // 踢掉最后一个
            Channel last = ChannelCacheHolder.get(id);
            if (last != null) {
                last.close();
            }
            SubscribeCacheHolder.remove(subject, id);
        }
    }
}
