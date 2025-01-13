package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.bytehonor.sdk.beautify.netty.common.util.NettyEnvUtils;

public class NettyTaskScheduler {

    private static final String NAMED = "netty-schedule-thread-";

    private final ScheduledExecutorService service;

    private NettyTaskScheduler() {
        int half = NettyEnvUtils.halfThreads();
        this.service = Executors.newScheduledThreadPool(half, new CustomizableThreadFactory(NAMED));
    }

    private static class LazyHolder {
        private static NettyTaskScheduler SINGLE = new NettyTaskScheduler();
    }

    private static NettyTaskScheduler self() {
        return LazyHolder.SINGLE;
    }

    private void scheduleAtFixedRate(NettyTask task, long delayMillis, long intervalMillis) {
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(task, delayMillis, intervalMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * 毫秒
     * 
     * @param task
     * @param delayMillis
     * @param intervalMillis
     */
    public static void schedule(NettyTask task, long delayMillis, long intervalMillis) {
        Objects.requireNonNull(task, "task");

        self().scheduleAtFixedRate(task, delayMillis, intervalMillis);
    }
}
