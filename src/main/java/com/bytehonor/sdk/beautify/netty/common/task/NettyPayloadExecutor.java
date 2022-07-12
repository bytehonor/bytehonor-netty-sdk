package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.handler.SubjectHandler;
import com.bytehonor.sdk.beautify.netty.common.handler.SubjectHandlerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

public class NettyPayloadExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPayloadExecutor.class);

    private final LinkedBlockingQueue<NettyPayload> queue = new LinkedBlockingQueue<NettyPayload>(10240);

    /**
     * 线程
     */
    private final Thread thread;

    private NettyPayloadExecutor() {
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
        thread.setName(NettyPayloadExecutor.class.getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start", thread.getName());
    }

    private void process() throws InterruptedException {
        // 从队列中取值,如果没有对象过期则队列一直等待，
        NettyPayload payload = queue.take();
        SubjectHandler handler = SubjectHandlerFactory.get(payload.getSubject());
        if (handler == null) {
            LOG.warn("no PayloadHandler! subject:{}", payload.getSubject());
            return;
        }

        handler.handle(payload);
    }

    private static class LazyHolder {
        private static final NettyPayloadExecutor SINGLE = new NettyPayloadExecutor();
    }

    private static NettyPayloadExecutor self() {
        return LazyHolder.SINGLE;
    }

    public static void add(NettyPayload payload) {
        if (payload == null) {
            LOG.warn("payload null");
            return;
        }
        try {
            self().queue.put(payload);
        } catch (Exception e) {
            LOG.error("add NettyPayload error", e);
        }
    }

}
