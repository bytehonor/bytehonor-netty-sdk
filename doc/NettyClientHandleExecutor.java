package com.bytehonor.sdk.beautify.netty.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.exception.NettyBeautifyException;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 多线程
 */
public class NettyClientHandleExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientHandleExecutor.class);

    private static final String NAMED = "netty-client-handle-";

    private final ExecutorService executor;

    private final Map<String, NettyClientHandler> map;

    private NettyClientHandleExecutor() {
        int nThreads = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(nThreads, new CustomizableThreadFactory(NAMED));
        this.map = new ConcurrentHashMap<String, NettyClientHandler>();
    }

    private static class LazyHolder {
        private static NettyClientHandleExecutor SINGLE = new NettyClientHandleExecutor();
    }

    private static NettyClientHandleExecutor self() {
        return LazyHolder.SINGLE;
    }

    private void execute(NettyTask task) {
        executor.execute(task);
    }

    public static void add(String stamp, NettyClientHandler handler) {
        self().map.put(stamp, handler);
    }

    // ---

    public static void onOpen(String stamp) {
        self().onOpenHandle(stamp);
    }

    public static void onClosed(String stamp, String msg) {
        self().onClosedHandle(stamp, msg);
    }

    public static void onError(String stamp, Throwable error) {
        self().onErrorHandle(stamp, error);
    }

    public static void onMessage(String stamp, Object text) {
        self().onMessageHandle(stamp, text);
    }

    //

    private void onErrorHandle(final String stamp, final Throwable error) {
        execute(new NettyTask() {

            @Override
            public void runInSafe() {
                NettyClientHandler handler = getHandler(stamp);
                handler.onError(stamp, error);
            }

        });
    }

    private void onMessageHandle(final String stamp, final Object msg) {
        execute(new NettyTask() {

            @Override
            public void runInSafe() {

                if (msg instanceof ByteBuf) {
                    ByteBuf buf = (ByteBuf) msg;
                    byte[] bytes = NettyDataUtils.readBytes(buf);
                    NettyDataUtils.validate(bytes);
                    String text = NettyDataUtils.parseData(bytes);
                    NettyClientHandler handler = getHandler(stamp);
                    handler.onMessage(stamp, text);
                } else {
                    Channel channel = StampChannelHolder.get(stamp);
                    LOG.error("doProcess unknown msg:{}, stamp:{}, {}", NettyChannelUtils.remarkMsg(msg), stamp,
                            NettyChannelUtils.remarkChannel(channel));
                }
            }

        });
    }

    private void onClosedHandle(final String stamp, final String text) {
        execute(new NettyTask() {

            @Override
            public void runInSafe() {
                NettyClientHandler handler = getHandler(stamp);
                handler.onClosed(stamp, text);
            }

        });
    }

    private void onOpenHandle(final String stamp) {
        execute(new NettyTask() {

            @Override
            public void runInSafe() {
                NettyClientHandler handler = getHandler(stamp);
                handler.onOpen(stamp);
            }

        });
    }

    private NettyClientHandler getHandler(String stamp) {
        NettyClientHandler handler = map.get(stamp);
        if (handler == null) {
            throw new NettyBeautifyException("handler null, stamp:" + stamp);
        }
        return handler;
    }
}
