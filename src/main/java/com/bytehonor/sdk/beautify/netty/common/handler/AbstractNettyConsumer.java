package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

public abstract class AbstractNettyConsumer<T> implements NettyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyConsumer.class);

    private final LinkedBlockingQueue<T> queue;

    /**
     * 线程
     */
    private final Thread thread;

    public AbstractNettyConsumer() {
        this(4096);
    }

    public AbstractNettyConsumer(int queues) {
        queue = new LinkedBlockingQueue<T>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        T payload = queue.take();
                        doConsume(payload);
                    } catch (Exception e) {
                        LOG.error("runInSafe error", e);
                    }
                }
            }

        });
        thread.setName(getClass().getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start", thread.getName());
    }

    public final void add(T payload) {
        if (payload == null) {
            LOG.warn("payload null");
            return;
        }
        try {
            queue.put(payload);
        } catch (Exception e) {
            LOG.error("add payload error", e);
        }
    }

    @Override
    public final String subject() {
        return target().getName();
    }

    @Override
    public final void consume(NettyPayload payload) {
        add(payload.reflect(target()));
    }

    public abstract Class<T> target();

    public abstract void doConsume(T payload);

}
