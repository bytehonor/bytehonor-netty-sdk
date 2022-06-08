package com.bytehonor.sdk.beautify.netty.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class ClasszUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClasszUtilsTest.class);

    @Test
    public void test() {

        boolean isOk = false;
        String name = NettyPayload.class.getName();
        try {
            Class<?> cz = ClasszUtils.find(name);
            isOk = name.equals(cz.getName());
            LOG.info("{}, {}", cz.getName(), isOk);
        } catch (ClassNotFoundException e) {
            LOG.info("error", e);
        }

        assertTrue("*test", isOk);
    }
}
