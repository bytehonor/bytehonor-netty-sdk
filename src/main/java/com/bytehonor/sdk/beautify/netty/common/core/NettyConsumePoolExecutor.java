package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * 多线程
 */
public class NettyConsumePoolExecutor {

    private static final String NAMED = "netty-consume-thread-";

    private final ExecutorService service;

    private NettyConsumePoolExecutor() {
        int nThreads = Runtime.getRuntime().availableProcessors();
        this.service = Executors.newFixedThreadPool(nThreads, new CustomizableThreadFactory(NAMED));
    }

    private static class LazyHolder {
        private static NettyConsumePoolExecutor SINGLE = new NettyConsumePoolExecutor();
    }

    private static NettyConsumePoolExecutor self() {
        return LazyHolder.SINGLE;
    }

    private void execute(NettyTask task) {
        service.execute(task);
    }

    /**
     * 
     * @param task
     */
    public static void add(NettyTask task) {
        Objects.requireNonNull(task, "task");

        self().execute(task);
    }
}
