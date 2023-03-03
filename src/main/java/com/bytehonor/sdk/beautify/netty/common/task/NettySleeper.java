package com.bytehonor.sdk.beautify.netty.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettySleeper {

    private static final Logger LOG = LoggerFactory.getLogger(NettySleeper.class);

    public static void sleep(long millis) {
        if (millis < 1L) {
            return;
        }

        LOG.debug("sleep millis:{}", millis);

        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            LOG.error("sleep", e);
        }
    }
}
