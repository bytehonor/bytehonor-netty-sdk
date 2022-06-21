package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.handler.PayloadHandler;
import com.bytehonor.sdk.beautify.netty.common.handler.PayloadHandlerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyPayloadTask {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPayloadTask.class);

    private final LinkedBlockingQueue<NettyPayload> queue = new LinkedBlockingQueue<NettyPayload>(10240);

    /**
     * 线程
     */
    private Thread thread;

    private NettyPayloadTask() {
        init();
    }

    /**
     * 初始化线程
     */
    private void init() {
        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        process();
                    } catch (Exception e) {
                        LOG.error("runInWhile error", e);
                    }
                }
            }

        });
        thread.setName(NettyPayloadTask.class.getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start", thread.getName());
    }

    private void process() throws InterruptedException {
        // 从队列中取值,如果没有对象过期则队列一直等待，
        NettyPayload payload = queue.take();

        PayloadHandler handler = PayloadHandlerFactory.get(payload.getSubject());
        if (handler == null) {
            LOG.warn("no PayloadHandler! subject:{}", payload.getSubject());
            return;
        }

        handler.handle(payload);
    }

    /**
     * 延迟加载(线程安全)
     *
     */
    private static class LazyHolder {
        private static NettyPayloadTask INSTANCE = new NettyPayloadTask();
    }

    private static NettyPayloadTask self() {
        return LazyHolder.INSTANCE;
    }

    public static void add(NettyPayload payload) {
        try {
            self().queue.put(payload);
        } catch (Exception e) {
            LOG.error("add NettyPayload error", e);
        }
    }

}
