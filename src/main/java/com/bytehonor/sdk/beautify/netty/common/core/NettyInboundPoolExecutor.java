package com.bytehonor.sdk.beautify.netty.common.core;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 多线程
 */
public class NettyInboundPoolExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(NettyInboundPoolExecutor.class);

    private static final String NAMED = "netty-inbound-thread-";

    private final ExecutorService executor;

    private NettyInboundPoolExecutor() {
        int nThreads = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(nThreads, new CustomizableThreadFactory(NAMED));
    }

    private static class LazyHolder {
        private static NettyInboundPoolExecutor SINGLE = new NettyInboundPoolExecutor();
    }

    private static NettyInboundPoolExecutor self() {
        return LazyHolder.SINGLE;
    }

    private void execute(NettyTask task) {
        executor.execute(task);
    }

    /**
     * 
     * @param task
     */
    public static void add(NettyTask task) {
        Objects.requireNonNull(task, "task");

        self().execute(task);
    }

    public static void onMessage(final String stamp, final Object msg, final BiConsumer<String, String> consumer) {
        add(new NettyTask() {

            @Override
            public void runInSafe() {
                doProcess(stamp, msg, consumer);
            }

        });
    }

    private static void doProcess(String stamp, Object msg, BiConsumer<String, String> consumer) {
        Objects.requireNonNull(consumer, "consumer");

        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = NettyDataUtils.readBytes(buf);
            NettyDataUtils.validate(bytes);
            String text = NettyDataUtils.parseData(bytes);
            consumer.accept(stamp, text);
        } else {
            Channel channel = StampChannelHolder.get(stamp);
            LOG.error("doProcess unknown msg:{}, stamp:{}, {}", doRemarkMsg(msg), stamp, doRemarkChannel(channel));
        }
    }

    private static String doRemarkChannel(Channel channel) {
        StringBuilder sb = new StringBuilder();
        sb.append("remoteAddress:").append(NettyChannelUtils.remoteAddress(channel));
        if (channel != null) {
            sb.append(", channelId:").append(channel.id().asLongText());
        }
        return sb.toString();
    }

    private static String doRemarkMsg(Object msg) {
        if (msg == null) {
            return "null";
        }
        return msg.toString();
    }
}
