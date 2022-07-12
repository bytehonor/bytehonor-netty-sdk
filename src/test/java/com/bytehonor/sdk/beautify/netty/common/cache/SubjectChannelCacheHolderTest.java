package com.bytehonor.sdk.beautify.netty.common.cache;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

public class SubjectChannelCacheHolderTest {

    private static final Logger LOG = LoggerFactory.getLogger(SubjectChannelCacheHolderTest.class);

    private static final String SUBJECT = "test";

    @Test
    public void test() {
        SubjectChannelCacheHolder.put(SUBJECT, new TestChannel());

        Thread thread1 = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                for (int i = 0; i < 100; i++) {
                    TestChannel channelId = new TestChannel();
                    SubjectChannelCacheHolder.put(SUBJECT, channelId);
                    if (i % 2 == 0) {
                        SubjectChannelCacheHolder.remove(SUBJECT, channelId);
                    }
                }

            }

        });
        thread1.setName("test-1");
        thread1.start();

        Thread thread2 = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                for (int i = 0; i < 100; i++) {
                    TestChannel channelId = new TestChannel();
                    SubjectChannelCacheHolder.put(SUBJECT, channelId);
                    if (i % 2 == 1) {
                        SubjectChannelCacheHolder.remove(SUBJECT, channelId);
                    }
                }

            }

        });
        thread2.setName("test-2");
        thread2.start();

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            LOG.error("sleep", e);
        }

        int size = SubjectChannelCacheHolder.list(SUBJECT).size();
        LOG.info("final size:{}", size);

        assertTrue("test", size == 101);
    }

}
