package com.bytehonor.sdk.beautify.netty.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;

/**
 * @author lijianqiang
 *
 */
public class NettyServerCheckTask extends NettyTask {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerCheckTask.class);

    @Override
    public void runInSafe() {
        int channelSize = ChannelCacheHolder.size();
        int stampSize = StampChannelHolder.size();
        LOG.info("channel size:{}, stamp size:{}", channelSize, stampSize);
    }

}
