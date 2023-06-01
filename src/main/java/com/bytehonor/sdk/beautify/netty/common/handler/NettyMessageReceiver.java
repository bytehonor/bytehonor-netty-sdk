package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.NettyReceiveMission;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * 独立线程
 * 
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    private static final AtomicLong AL = new AtomicLong(0);

    private final NettyFrameHandlerFactory handlers;

    private final NettyConsumerFactory consumers;

    /**
     * 队列
     */
    private final LinkedBlockingQueue<NettyReceiveMission> queue;
    /**
     * 线程
     */
    private final Thread thread;

    public NettyMessageReceiver() {
        this(40960);
    }

    public NettyMessageReceiver(int queues) {
        handlers = new NettyFrameHandlerFactory();
        consumers = new NettyConsumerFactory();
        queue = new LinkedBlockingQueue<NettyReceiveMission>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyReceiveMission mission = queue.take();
                        process(mission);
                    } catch (Exception e) {
                        LOG.error("runInSafe error", e);
                    }
                }
            }

        });

        thread.setName(NettyMessageReceiver.class.getSimpleName() + "-" + AL.incrementAndGet());
        thread.start();
        LOG.info("[Thread] {} start, queues:{}", thread.getName(), queues);
    }

    public final void addHandler(NettyFrameHandler handler) {
        this.handlers.add(handler);
    }

    public final void addConsumer(NettyConsumer consumer) {
        this.consumers.add(consumer);
    }

    private void process(NettyReceiveMission mission) {
        String stamp = mission.getStamp();
        NettyFrame frame = NettyFrame.fromJson(mission.getText());
        if (LOG.isDebugEnabled()) {
            LOG.debug("process method:{}, subject:{}, stamp:{}", frame.getMethod(), frame.getSubject(), stamp);
        }

        NettyFrameHandler handler = handlers.get(frame.getMethod());
        if (handler == null) {
            LOG.info("handler null, method:{}", frame.getMethod());
            return;
        }

        NettyPayload payload = NettyPayload.of(frame.getSubject(), frame.getBody());
        handler.handle(stamp, payload, consumers);
    }

    public final void addMission(NettyReceiveMission mission) {
        if (mission == null) {
            LOG.warn("mission null");
            return;
        }
        try {
            queue.put(mission);
        } catch (Exception e) {
            LOG.error("addMessage error", e);
        }
    }
}
