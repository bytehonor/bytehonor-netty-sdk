package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandler;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.netty.bytehonor.common.listener.ClientListener;
import com.bytehonor.sdk.netty.bytehonor.common.listener.DefaultClientListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyScheduleTaskExecutor;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyTaskBuilder;

public final class NettyClientContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);

    private NettyClient client;

    private NettyConfig config;

    private ClientListener listener;

    private NettyClientContanier() {
    }

    /**
     * 延迟加载(线程安全)
     *
     */
    private static class LazyHolder {
        private static NettyClientContanier instance = new NettyClientContanier();
    }

    private static NettyClientContanier getInstance() {
        return LazyHolder.instance;
    }

    public static void connect(String host, int port) {
        connect(NettyConfigBuilder.client(host, port).build(), new DefaultClientListener());
    }

    public static void connect(String host, int port, ClientListener listener) {
        connect(NettyConfigBuilder.client(host, port).build(), listener);
    }

    /**
     * 有启动ping任务，只能调一次，重连用reconnect
     * 
     * @param config
     * @param listener
     */
    public static void connect(NettyConfig config, ClientListener listener) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(config.getHost(), "host");
        Objects.requireNonNull(listener, "listener");

        LOG.info("connect begin ...");
        getInstance().config = config;
        getInstance().listener = listener;

        doConnect();

        pingFixedRate();
    }

    private static void doConnect() {
        Objects.requireNonNull(getInstance().config, "config");

        getInstance().client = new NettyClient(getInstance().config, getInstance().listener);
        getInstance().client.start();
    }

    public static void ping() {
        getInstance().client.ping();
    }

    public static void pingFixedRate() {
        NettyScheduleTaskExecutor.scheduleAtFixedRate(NettyTaskBuilder.clientPing(), 30L, 45L);
    }

    public static void reconnect() {
        LOG.info("reconnect begin ...");
        if (getInstance().client != null) {
            getInstance().client.close();
        }
        doConnect();

        for (int i = 0; i < 40; i++) {
            try {
                Thread.sleep(250L);
            } catch (Exception e) {
                LOG.error("sleep", e);
            }
            if (isConnected()) {
                LOG.info("reconnect connected ok, i:{}", i);
                break;
            }
        }
        if (isConnected() == false) {
            LOG.error("reconnect connected failed");
            return;
        }
    }

    public static boolean isConnected() {
        return getInstance().client.isConnected();
    }

    public static void send(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        getInstance().client.send(payload);
    }

    public static void subscribe(String subjects) {
        if (subjects == null) {
            return;
        }
        if (isConnected() == false) {
            throw new BytehonorNettySdkException("subscribe should be after connected");
        }
        getInstance().client.subscribe(subjects);
    }

    public static void unsubscribe(String subjects) {
        if (subjects == null) {
            return;
        }
        if (isConnected() == false) {
            throw new BytehonorNettySdkException("unsubscribe should be after connected");
        }
        getInstance().client.unsubscribe(subjects);
    }

    public static void addHandler(PayloadHandler handler) {
        Objects.requireNonNull(handler, "handler");
        Objects.requireNonNull(handler.subject(), "subject");
        PayloadHandlerFactory.put(handler);
    }
}
