package com.bytehonor.sdk.netty.bytehonor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandler;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.netty.bytehonor.common.limiter.NettySubscribeSubjectLimiter;
import com.bytehonor.sdk.netty.bytehonor.common.limiter.SubjectLimitation;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyScheduleTaskExecutor;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyServerCheckTask;

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
        start(NettyConfigBuilder.server(port).build());
    }

    public static void start(NettyConfig config) {
        LOG.info("start...");
        getInstance().server.start(config);
        NettyScheduleTaskExecutor.scheduleAtFixedRate(new NettyServerCheckTask(), 30L, config.getPeriodSeconds());
    }

    public static void handle(PayloadHandler handler) {
        if (handler == null) {
            return;
        }
        PayloadHandlerFactory.put(handler);
    }

    public static void addLimitation(SubjectLimitation limitation) {
        NettySubscribeSubjectLimiter.add(limitation);
    }
}
