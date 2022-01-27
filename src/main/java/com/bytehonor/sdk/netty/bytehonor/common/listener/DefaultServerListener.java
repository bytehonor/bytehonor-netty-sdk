package com.bytehonor.sdk.netty.bytehonor.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServerListener implements ServerListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultServerListener.class);

    @Override
    public void onTotal(int total) {
        LOG.info("Server onTotal:{}", total);
    }

}
