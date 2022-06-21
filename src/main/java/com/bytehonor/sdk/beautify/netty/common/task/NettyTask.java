package com.bytehonor.sdk.beautify.netty.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijianqiang
 *
 */
public abstract class NettyTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyTask.class);

    @Override
    public final void run() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} run", this.getClass().getSimpleName());
        }
        try {
            runInSafe();
        } catch (Exception e) {
            LOG.error("NettyTask error", e);
        }
    }

    public abstract void runInSafe();
}
