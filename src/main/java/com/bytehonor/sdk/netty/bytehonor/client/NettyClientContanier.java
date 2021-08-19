package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;

public final class NettyClientContanier {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);

    private NettyClient client;

    private String host;

    private int port;

    private boolean ping;

    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

    private NettyClientContanier() {
        this.ping = false;
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
        LOG.info("connect begin ...");
        getInstance().host = host;
        getInstance().port = port;
        if (getInstance().client == null) {
            getInstance().client = new NettyClient(host, port);
        }
        getInstance().client.start();
        ping();
    }

    private static void ping() {
        if (getInstance().ping == false) {
            LOG.info("ping begin ...");
            getInstance().ping = true;
            // 连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // task to run goes here
                    try {
                        NettyMessageSender.ping(getInstance().client.getChannel());
                    } catch (Exception e) {
                        LOG.error("ping error:{}", e.getMessage());
                        reconnect();
                    }
                }
            };
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            SERVICE.scheduleAtFixedRate(runnable, 30, 55, TimeUnit.SECONDS);
        }
    }

    public static void reconnect() {
        LOG.info("reconnect begin ...");
        getInstance().client = new NettyClient(getInstance().host, getInstance().port);

        try {
            getInstance().client.start();
        } catch (Exception e) {
            LOG.error("reconnect error", e);
        }
    }

    public static boolean isConnected() {
        return getInstance().client.getChannel().isActive();
    }

    public static void send(String value) {
        NettyMessageSender.send(getInstance().client.getChannel(), value);
    }
}
