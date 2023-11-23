package com.bytehonor.sdk.beautify.netty.common.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayloadMission;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * @author lijianqiang
 *
 * @param <T>
 */
public abstract class AbstractNettyConsumer<T> implements NettyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyConsumer.class);

    private final LinkedBlockingQueue<NettyPayloadMission> queue;

    /**
     * 线程
     */
    private final Thread thread;

    public AbstractNettyConsumer() {
        this(20480);
    }

    public AbstractNettyConsumer(int queues) {
        queue = new LinkedBlockingQueue<NettyPayloadMission>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyPayloadMission mission = queue.take();
                        doMission(mission);
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

    private void doPut(NettyPayloadMission mission) {
        if (mission == null || mission.getPayload() == null) {
            LOG.warn("mission or paylaod null");
            return;
        }
        try {
            queue.put(mission);
        } catch (Exception e) {
            LOG.error("add mission error", e);
        }
    }

    private void doMission(NettyPayloadMission mission) {
        process(mission.getStamp(), mission.getPayload().reflect(target()));
    }

    @Override
    public final String subject() {
        return target().getName();
    }

    @Override
    public final void consume(String stamp, NettyPayload payload) {
        doPut(NettyPayloadMission.of(stamp, payload));
    }

    public abstract Class<T> target();

    public abstract void process(String stamp, T target);

}
