package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.beautify.netty.common.cache.ChannelIdHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.SubjectChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.WhoisChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.server.NettyServerContanier;

import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
public class NettyServerCheckTask extends NettyTask {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void runInSafe() {
        LOG.info("channel size:{}, subscribe size:{}, whois size:{}", ChannelCacheHolder.size(),
                SubjectChannelCacheHolder.size(), WhoisChannelCacheHolder.size());

        Iterator<Entry<String, ChannelIdHolder>> its = SubjectChannelCacheHolder.entrySet().iterator();
        while (its.hasNext()) {
            Entry<String, ChannelIdHolder> item = its.next();
            String subject = item.getKey();
            ChannelIdHolder holder = item.getValue();
            LOG.info("subject:{}, channels size:{}", subject, holder.size());
            if (holder.size() > 0) {
                Set<ChannelId> channels = holder.values();
                for (ChannelId channel : channels) {
                    if (ChannelCacheManager.exists(channel)) {
                        continue;
                    }
                    LOG.warn("remove subject:{}, channel:{}", subject, channel.asLongText());
                    holder.remove(channel);
                }
            }
        }

        // 限幅
        NettyServerContanier.limit();
    }

}
