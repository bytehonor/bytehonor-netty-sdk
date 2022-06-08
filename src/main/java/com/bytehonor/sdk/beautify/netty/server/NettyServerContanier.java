package com.bytehonor.sdk.beautify.netty.server;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.handler.PayloadHandler;
import com.bytehonor.sdk.beautify.netty.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.beautify.netty.common.limiter.SubjectLimiter;
import com.bytehonor.sdk.beautify.netty.common.limiter.SubjectSubscribeLimiter;
import com.bytehonor.sdk.beautify.netty.common.listener.DefaultServerListener;
import com.bytehonor.sdk.beautify.netty.common.listener.ServerListener;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.beautify.netty.common.task.NettyScheduleTaskExecutor;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTaskBuilder;

public class NettyServerContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerContanier.class);

    private NettyServer server;

    private NettyServerContanier() {
        this.server = new NettyServer();
    }

    /**
     * 延迟加载(线程安全)
     *
     */
    private static class LazyHolder {
        private static NettyServerContanier instance = new NettyServerContanier();
    }

    private static NettyServerContanier getInstance() {
        return LazyHolder.instance;
    }

    public static void start(int port) {
        start(port, new DefaultServerListener());
    }

    public static void start(int port, ServerListener listener) {
        start(NettyConfigBuilder.server(port).build(), listener);
    }

    public static void start(NettyConfig config) {
        start(config, new DefaultServerListener());
    }

    public static void start(NettyConfig config, ServerListener listener) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(listener, "listener");
        LOG.info("start...");

        getInstance().server.start(config, listener);

        NettyScheduleTaskExecutor.scheduleAtFixedRate(NettyTaskBuilder.serverCheck(), 20L, config.getPeriodSeconds());
    }

    public static void addHandler(PayloadHandler handler) {
        Objects.requireNonNull(handler, "handler");
        Objects.requireNonNull(handler.subject(), "subject");
        PayloadHandlerFactory.put(handler);
    }

    public static void addLimiter(String subject, int limit) {
        Objects.requireNonNull(subject, "subject");
        SubjectSubscribeLimiter.add(SubjectLimiter.of(subject, limit));
    }

    public static void limit() {
        SubjectSubscribeLimiter.limits();
    }
}
