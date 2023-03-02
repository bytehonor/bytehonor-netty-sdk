package com.bytehonor.sdk.beautify.netty.client;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.exception.NettyBeautifyException;
import com.bytehonor.sdk.beautify.netty.common.model.NettyClientConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyScheduleTaskExecutor;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTaskBuilder;

/**
 * @author lijianqiang
 *
 */
@Deprecated
public final class NettyClientContanier {

//    private static final Logger LOG = LoggerFactory.getLogger(NettyClientContanier.class);
//
//    private final Set<String> subjects;
//
//    private NettyClient client;
//
//    private NettyConfig config;
//
//    private NettyClientHandler listener;
//
//    private NettyClientContanier() {
//        this.subjects = new HashSet<String>();
//    }
//
//    /**
//     * 延迟加载(线程安全)
//     *
//     */
//    private static class LazyHolder {
//        private static NettyClientContanier instance = new NettyClientContanier();
//    }
//
//    private static NettyClientContanier getInstance() {
//        return LazyHolder.instance;
//    }
//
//    public static void connect(String host, int port) {
//        connect(NettyConfigBuilder.client(host, port).build(), new DefaultNettyClientListener());
//    }
//
//    public static void connect(String host, int port, NettyClientHandler listener) {
//        connect(NettyConfigBuilder.client(host, port).build(), listener);
//    }
//
//    /**
//     * 有启动ping任务，只能调一次，重连用reconnect
//     * 
//     * @param config
//     * @param listener
//     */
//    public static void connect(NettyConfig config, NettyClientHandler listener) {
//        Objects.requireNonNull(config, "config");
//        Objects.requireNonNull(config.getHost(), "host");
//        Objects.requireNonNull(listener, "listener");
//
//        LOG.info("connect begin ...");
//        getInstance().config = config;
//        getInstance().listener = listener;
//
//        doConnect();
//
//        pingFixedRate();
//    }
//
//    private static void doConnect() {
//        Objects.requireNonNull(getInstance().config, "config");
//
//        getInstance().client = new NettyClient(getInstance().config, getInstance().listener);
//        getInstance().client.start();
//    }
//
//    public static void ping() {
//        getInstance().client.ping();
//    }
//
//    public static void pingFixedRate() {
//        NettyScheduleTaskExecutor.scheduleAtFixedRate(NettyTaskBuilder.clientPing(), 30L, 45L);
//    }
//
//    public static void reconnect() {
//        LOG.info("reconnect begin ...");
//        if (getInstance().client != null) {
//            getInstance().client.close();
//        }
//        doConnect();
//
//        for (int i = 0; i < 40; i++) {
//            try {
//                Thread.sleep(250L);
//            } catch (Exception e) {
//                LOG.error("sleep", e);
//            }
//            if (isConnected()) {
//                LOG.info("reconnect connected ok, i:{}", i);
//                break;
//            }
//        }
//        if (isConnected() == false) {
//            LOG.error("reconnect connected failed");
//            return;
//        }
//    }
//
//    public static boolean isConnected() {
//        return getInstance().client.isConnected();
//    }
//
//    public static void send(NettyPayload payload) {
//        Objects.requireNonNull(payload, "payload");
//        getInstance().client.send(payload);
//    }
//
//    public static void subscribe() {
//        if (isConnected() == false) {
//            throw new NettyBeautifyException("subscribe should be after connected");
//        }
//        Set<String> subjects = getInstance().subjects;
//        if (CollectionUtils.isEmpty(subjects)) {
//            throw new NettyBeautifyException("subscribe subjects cannt be empty");
//        }
//        for (String subject : subjects) {
//            getInstance().client.subscribe(subject);
//        }
//    }
//
//    public static void unsubscribe() {
//        if (isConnected() == false) {
//            throw new NettyBeautifyException("unsubscribe should be after connected");
//        }
//        Set<String> subjects = getInstance().subjects;
//        if (CollectionUtils.isEmpty(subjects)) {
//            LOG.warn("unsubscribe subjects already empty");
//            return;
//        }
//        for (String subject : subjects) {
//            getInstance().client.unsubscribe(subject);
//        }
//        getInstance().subjects.clear();
//    }
//
//    public static void addConsumer(NettyConsumer consumer) {
//        Objects.requireNonNull(consumer, "consumer");
//        Objects.requireNonNull(consumer.subject(), "subject");
//
//        getInstance().subjects.add(consumer.subject());
//        NettyConsumerFactory.add(consumer);
//    }
}
