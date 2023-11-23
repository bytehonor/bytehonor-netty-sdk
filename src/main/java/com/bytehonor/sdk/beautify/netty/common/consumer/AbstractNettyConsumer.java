package com.bytehonor.sdk.beautify.netty.common.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayloadPack;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * @author lijianqiang
 *
 * @param <T>
 */
public abstract class AbstractNettyConsumer<T> implements NettyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyConsumer.class);

    private final LinkedBlockingQueue<NettyPayloadPack> queue;

    /**
     * 线程
     */
    private final Thread thread;

    public AbstractNettyConsumer() {
        this(20480);
    }

    public AbstractNettyConsumer(int queues) {
        queue = new LinkedBlockingQueue<NettyPayloadPack>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyPayloadPack pack = queue.take();
                        doMission(pack);
                    } catch (Exception e) {
                        LOG.error("runInSafe error", e);
                    }
                }
            }

        });
        thread.setName(getClass().getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start, queues:{}", thread.getName(), queues);
    }

    private void doAdd(NettyPayloadPack pack) {
        if (pack == null || pack.getPayload() == null) {
            LOG.warn("pack or paylaod null");
            return;
        }
        try {
            queue.put(pack);
        } catch (Exception e) {
            LOG.error("add pack error", e);
        }
    }

    private void doMission(NettyPayloadPack pack) {
        process(pack.getStamp(), pack.getPayload().reflect(target()));
    }

    @Override
    public final String subject() {
        return target().getName();
    }

    @Override
    public final void consume(String stamp, NettyPayload payload) {
        doAdd(NettyPayloadPack.of(stamp, payload));
    }

    public abstract Class<T> target();

    public abstract void process(String stamp, T target);

}
