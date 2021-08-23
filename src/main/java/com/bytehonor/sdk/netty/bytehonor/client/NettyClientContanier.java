package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;

public final class NettyClientContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);

    private NettyClient client;

    private String host;

    private int port;

    private NettyConfig config;

    private static boolean pinged = false;

    private static boolean whoised = false;

    private static final Set<String> SET = new HashSet<String>();

    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

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
        connect(host, port, new NettyConfig());
    }

    public static void connect(String host, int port, NettyConfig config) {
        Objects.requireNonNull(host, "host");
        Objects.requireNonNull(config, "config");

        LOG.info("connect begin ...");
        getInstance().host = host;
        getInstance().port = port;
        getInstance().config = config;
        if (getInstance().client != null) {
            getInstance().client.getChannel().close();
        }
        getInstance().client = new NettyClient(host, port, config);
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

            scheduleAtFixedRate(runnable, 20, 45);
        }
    }

    public static void startWhois(final String id) {
        if (whoised == false) {
            LOG.info("startWhois begin ...");
            whoised = true;
            // 连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        whois(id);
                    } catch (Exception e) {
                        LOG.error("report error:{}", e.getMessage());
                        reconnect();
                    }
                }
            };

            scheduleAtFixedRate(runnable, 25, 300);
        }
    }

    public static void reconnect() {
        LOG.info("reconnect begin ...");
        connect(getInstance().host, getInstance().port, getInstance().config);
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

    public static void send(String value) {
        getInstance().client.send(value);
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

    public static void whois(String id) {
        getInstance().client.whois(id);
    }

    public static void ping() {
        getInstance().client.ping();
    }

    public static void scheduleAtFixedRate(Runnable command, long delaySeconds, long periodSeconds) {
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        SERVICE.scheduleAtFixedRate(command, delaySeconds, periodSeconds, TimeUnit.SECONDS);
    }
}
