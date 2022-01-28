package com.bytehonor.sdk.netty.bytehonor.server;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandler;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.netty.bytehonor.common.limiter.NettySubscribeSubjectLimiter;
import com.bytehonor.sdk.netty.bytehonor.common.limiter.SubjectLimiter;
import com.bytehonor.sdk.netty.bytehonor.common.listener.DefaultServerListener;
import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListenerHelper;
import com.bytehonor.sdk.netty.bytehonor.common.listener.ServerListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyScheduleTaskExecutor;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyTaskBuilder;

public class NettyServerContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerContanier.class);

    private NettyServer server;

    private ServerListener listener;

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
        start(NettyConfigBuilder.server(port).build(), new DefaultServerListener());
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

        getInstance().listener = listener;

        getInstance().server.start(config);

        NettyScheduleTaskExecutor.scheduleAtFixedRate(NettyTaskBuilder.serverCheck(), 30L, config.getPeriodSeconds());
    }

    public static void addHandler(PayloadHandler handler) {
        Objects.requireNonNull(handler, "handler");
        Objects.requireNonNull(handler.subject(), "subject");
        PayloadHandlerFactory.put(handler);
    }

    public static void addLimiter(String subject, int limit) {
        Objects.requireNonNull(subject, "subject");
        NettySubscribeSubjectLimiter.add(SubjectLimiter.of(subject, limit));
    }

    public static void onTotal(int total) {
        NettyListenerHelper.onTotal(getInstance().listener, total);
    }

    public static void limit() {
        NettySubscribeSubjectLimiter.limit();
    }
}
