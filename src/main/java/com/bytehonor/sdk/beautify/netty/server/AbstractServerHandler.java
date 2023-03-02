package com.bytehonor.sdk.beautify.netty.server;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumer;
import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

/**
 * @author lijianqiang
 *
 */
public abstract class AbstractServerHandler implements NettyServerHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServerHandler.class);

    private final LinkedBlockingQueue<NettyMessage> queue;

    private final NettyConsumerFactory factory;
    /**
     * 线程
     */
    private final Thread thread;

    public AbstractServerHandler() {
        this(40960);
    }

    public AbstractServerHandler(int queues) {
        queue = new LinkedBlockingQueue<NettyMessage>(queues);

        factory = new NettyConsumerFactory();

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyMessage message = queue.take();
                        doConsume(message);
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

    private void add(NettyMessage message) {
        if (message == null) {
            LOG.warn("message null");
            return;
        }
        try {
            queue.put(message);
        } catch (Exception e) {
            LOG.error("add message error", e);
        }
    }

    private void doConsume(NettyMessage message) {
        // LOG.info("doConsume text:{}, stamp:{}", message.getText(),
        // message.getStamp());

        NettyFrame frame = NettyFrame.fromJson(message.getText());
        if (NettyFrame.PING.equals(frame.getMethod())) {
            NettyMessageSender.pong(message.getStamp());
            return;
        }
        if (NettyFrame.PONG.equals(frame.getMethod())) {
            return;
        }

        onPorcess(message.getStamp(), NettyPayload.of(frame.getSubject(), frame.getBody()));
    }

    private void onPorcess(String stamp, NettyPayload payload) {
        LOG.info("onPorcess subject:{}, stamp:{}", payload.getSubject(), stamp);
        NettyConsumer consumer = factory.get(payload.getSubject());
        if (consumer == null) {
            LOG.warn("onPorcess no consumer, subject:{}, body:{}, stamp:{}", payload.getSubject(), payload.getBody(),
                    stamp);
            return;
        }
        consumer.consume(payload);
    }

    @Override
    public final void onMessage(NettyMessage message) {
        add(message);
    }

    public final void addConsumer(NettyConsumer consumer) {
        this.factory.add(consumer);
    }
}
