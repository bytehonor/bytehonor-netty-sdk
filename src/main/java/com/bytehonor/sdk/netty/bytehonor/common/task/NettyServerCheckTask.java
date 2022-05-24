package com.bytehonor.sdk.netty.bytehonor.common.task;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ClientChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.netty.bytehonor.common.cache.WhoisChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubjectChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.server.NettyServerContanier;

import io.netty.channel.ChannelId;

public class NettyServerCheckTask extends NettyTask {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void runInSafe() {
        LOG.info("channel size:{}, subscribe size:{}, whois size:{}/{}", ClientChannelCacheHolder.size(),
                SubjectChannelCacheHolder.size(), WhoisChannelCacheHolder.whoisSize(),
                WhoisChannelCacheHolder.channelSize());

        Iterator<Entry<String, Set<ChannelId>>> its = SubjectChannelCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, Set<ChannelId>> item = its.next();
            String subject = item.getKey();
            Set<ChannelId> channels = item.getValue();
            LOG.info("subject:{}, channels size:{}", subject, channels.size());
            for (ChannelId channel : channels) {
                if (ChannelCacheManager.getChannel(channel) == null) {
                    LOG.warn("remove subject:{}, channel:{}", subject, channel.asLongText());
                    SubjectChannelCacheHolder.remove(subject, channel);
                }
            }
        }

        // 限幅
        NettyServerContanier.limit();
    }

}
