package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandler;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyScheduleTaskExecutor;

public final class NettyClientContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);

    private NettyClient client;

    private NettyConfig config;

    private static boolean pinged = false;

    private static final Set<String> SET = new HashSet<String>();

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
        connect(NettyConfigBuilder.client(host, port).build());
    }

    public static void connect(NettyConfig config) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(config.getHost(), "host");

        LOG.info("connect begin ...");
        getInstance().config = config;
        if (getInstance().client != null) {
            getInstance().client.getChannel().close();
        }

        getInstance().client = new NettyClient(config);
        getInstance().client.start();
        startPing();
    }

    private static void startPing() {
        if (pinged == false) {
            LOG.info("startPing begin ...");
            pinged = true;
            // 连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // task to run goes here
                    try {
                        ping();
                    } catch (Exception e) {
                        LOG.error("ping error:{}", e.getMessage());
                        reconnect();
                    }
                }
            };

            NettyScheduleTaskExecutor.scheduleAtFixedRate(runnable, 20L, 45L);
        }
    }

    public static void reconnect() {
        LOG.info("reconnect begin ...");
        connect(getInstance().config);
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                LOG.error("sleep", e);
            }
            if (isConnected()) {
                LOG.info("reconnect connected ok");
                break;
            }
        }
        if (isConnected() == false) {
            LOG.error("reconnect connected failed");
            return;
        }
        // 把任务重新订阅
        if (SET.isEmpty() == false) {
            LOG.info("subscribe again ...");
            for (String category : SET) {
                subscribe(category);
            }
        }
    }

    public static boolean isConnected() {
        return getInstance().client.isConnected();
    }

    public static void send(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        getInstance().client.send(payload);
    }

    public static void subscribe(String names) {
        if (names == null) {
            return;
        }
        if (SET.contains(names) == false) {
            SET.add(names);
        }
        getInstance().client.subscribe(names);
    }

    public static void unsubscribe(String names) {
        if (names == null) {
            return;
        }
        if (SET.contains(names)) {
            SET.remove(names);
        }
        getInstance().client.unsubscribe(names);
    }

    public static void ping() {
        getInstance().client.ping();
    }

    public static void handle(PayloadHandler handler) {
        Objects.requireNonNull(handler, "handler");
        if (isConnected() == false) {
            throw new BytehonorNettySdkException("Handle should be after connected");
        }
        PayloadHandlerFactory.put(handler);
        subscribe(handler.name());
    }
}
