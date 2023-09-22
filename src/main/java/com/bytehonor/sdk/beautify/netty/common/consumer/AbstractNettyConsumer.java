package com.bytehonor.sdk.beautify.netty.common.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConsumeMission;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * @author lijianqiang
 *
 * @param <T>
 */
public abstract class AbstractNettyConsumer<T> implements NettyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyConsumer.class);

    private final LinkedBlockingQueue<NettyConsumeMission<T>> queue;

    /**
     * 线程
     */
    private final Thread thread;

    public AbstractNettyConsumer() {
        this(20480);
    }

    public AbstractNettyConsumer(int queues) {
        queue = new LinkedBlockingQueue<NettyConsumeMission<T>>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyConsumeMission<T> mission = queue.take();
                        process(mission.getStamp(), mission.getTarget());
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

    public final void add(NettyConsumeMission<T> mission) {
        if (mission == null || mission.getTarget() == null) {
            LOG.warn("mission or target null");
            return;
        }
        try {
            queue.put(mission);
        } catch (Exception e) {
            LOG.error("add mission error", e);
        }
    }

    @Override
    public final String subject() {
        return target().getName();
    }

    @Override
    public final void consume(String stamp, NettyPayload payload) {
        add(NettyConsumeMission.of(stamp, payload.reflect(target())));
    }

    public abstract Class<T> target();

    public abstract void process(String stamp, T target);

}
