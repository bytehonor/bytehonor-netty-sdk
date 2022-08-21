package com.bytehonor.sdk.beautify.netty.common.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * @author lijianqiang
 *
 */
public class NettyConsumerExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(NettyConsumerExecutor.class);

    private final LinkedBlockingQueue<String> queue;

    /**
     * 线程
     */
    private final Thread thread;

    private NettyConsumerExecutor() {
        queue = new LinkedBlockingQueue<String>(10240);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        doProcess();
                    } catch (Exception e) {
                        LOG.error("runInWhile error", e);
                    }
                }
            }

        });
        thread.setName(NettyConsumerExecutor.class.getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start", thread.getName());
    }

    private void doProcess() throws InterruptedException {
        // 从队列中取值,如果没有对象过期则队列一直等待，
        String message = queue.take();
        NettyPayload payload = NettyPayload.fromJson(message);
        if (LOG.isDebugEnabled()) {
            LOG.debug("subject:{}, body:{}", payload.getSubject(), payload.getBody());
        }
        NettyConsumer consumer = NettyConsumerFactory.get(payload.getSubject());
        if (consumer == null) {
            LOG.warn("no consumer! subject:{}", payload.getSubject());
            return;
        }

        consumer.consume(payload);
    }

    private static class LazyHolder {
        private static final NettyConsumerExecutor SINGLE = new NettyConsumerExecutor();
    }

    private static NettyConsumerExecutor self() {
        return LazyHolder.SINGLE;
    }

    public static void add(String message) {
        if (message == null) {
            LOG.warn("payload message null");
            return;
        }
        try {
            self().queue.put(message);
        } catch (Exception e) {
            LOG.error("add payload message error", e);
        }
    }

}
