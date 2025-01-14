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
import com.bytehonor.sdk.beautify.netty.common.util.NettyEnvUtils;

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
        int full = NettyEnvUtils.fullThreads();
        this.executor = Executors.newFixedThreadPool(full, new CustomizableThreadFactory(NAMED));
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
    private static void add(NettyTask task) {
        Objects.requireNonNull(task, "task");

        self().execute(task);
    }

    public static void onMessage(final String stamp, final Object msg, final BiConsumer<String, String> consumer) {
        add(new NettyTask() {

            @Override
            public void runInSafe() {
                doMessage(stamp, msg, consumer);
            }

        });
    }

    private static void doMessage(String stamp, Object msg, BiConsumer<String, String> consumer) {
        Objects.requireNonNull(consumer, "consumer");

        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = NettyDataUtils.readBytes(buf);
            NettyDataUtils.validate(bytes);
            String text = NettyDataUtils.parseData(bytes);
            consumer.accept(stamp, text);
        } else {
            Channel channel = StampChannelHolder.get(stamp);
            LOG.error("doMessage unknown msg:{}, stamp:{}, {}", NettyChannelUtils.remarkMsg(msg), stamp,
                    NettyChannelUtils.remarkChannel(channel));
        }
    }
}
