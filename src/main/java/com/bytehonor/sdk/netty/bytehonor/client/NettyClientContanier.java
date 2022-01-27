package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandler;
import com.bytehonor.sdk.netty.bytehonor.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.task.NettyScheduleTaskExecutor;

public final class NettyClientContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);

    private NettyClient client;

    private NettyConfig config;

    private NettyListener listener;

    private static boolean pinged = false;

    // private static final Set<String> SUBJECTS = new HashSet<String>();

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

    public static void connect(String host, int port, NettyListener listener) {
        connect(NettyConfigBuilder.client(host, port).build(), listener);
    }

    public static void connect(NettyConfig config, NettyListener listener) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(config.getHost(), "host");

        LOG.info("connect begin ...");
        if (getInstance().config == null) {
            getInstance().config = config;
        }
        if (getInstance().client != null) {
            getInstance().client.close();
        }

        getInstance().client = new NettyClient(config, listener);
        getInstance().client.start();
    }

    public static void ping() {
        if (pinged == false) {
            LOG.info("ping task begin ...");
            pinged = true;
            // 连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // task to run goes here
                    try {
                        doPing();
                    } catch (Exception e) {
                        LOG.error("ping error:{}", e.getMessage());
                        doReconnect();
                    }
                }
            };

            NettyScheduleTaskExecutor.scheduleAtFixedRate(runnable, 20L, 45L);
        }
    }

    private static void doReconnect() {
        LOG.info("reconnect begin ...");
        connect(getInstance().config, getInstance().listener);
        for (int i = 0; i < 40; i++) {
            try {
                Thread.sleep(250L);
            } catch (Exception e) {
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
//        if (SUBJECTS.isEmpty() == false) {
//            LOG.info("subscribe again ...");
//            for (String subject : SUBJECTS) {
//                subscribe(subject);
//            }
//        }
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
//        if (SUBJECTS.contains(subjects) == false) {
//            SUBJECTS.add(subjects);
//        }
        getInstance().client.subscribe(subjects);
    }

    public static void unsubscribe(String subjects) {
        if (subjects == null) {
            return;
        }
        if (isConnected() == false) {
            throw new BytehonorNettySdkException("unsubscribe should be after connected");
        }
//        if (SUBJECTS.contains(subjects)) {
//            SUBJECTS.remove(subjects);
//        }
        getInstance().client.unsubscribe(subjects);
    }

    private static void doPing() {
        getInstance().client.ping();
    }

    public static void addHandler(PayloadHandler handler) {
        Objects.requireNonNull(handler, "handler");
        Objects.requireNonNull(handler.subject(), "subject");
        PayloadHandlerFactory.put(handler);
    }
}
