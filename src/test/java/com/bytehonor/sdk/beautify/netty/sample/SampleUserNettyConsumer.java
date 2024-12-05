package com.bytehonor.sdk.beautify.netty.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.core.AbstractNettyConsumer;

public class SampleUserNettyConsumer extends AbstractNettyConsumer<SampleUser> {

    private static final Logger LOG = LoggerFactory.getLogger(SampleUserNettyConsumer.class);

    @Override
    public Class<SampleUser> target() {
        return SampleUser.class;
    }

    @Override
    public void process(String stamp, SampleUser target) {
        LOG.info("stamp:{}, target:{}", stamp, target.getId());
    }

}
