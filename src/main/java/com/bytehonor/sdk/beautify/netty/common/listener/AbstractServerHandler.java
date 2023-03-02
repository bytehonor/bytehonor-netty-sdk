package com.bytehonor.sdk.beautify.netty.common.listener;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * 线程
     */
    private final Thread thread;

    public AbstractServerHandler() {
        this(4096);
    }

    public AbstractServerHandler(int queues) {
        queue = new LinkedBlockingQueue<NettyMessage>(queues);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettyMessage payload = queue.take();
                        doConsume(payload);
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

    private void add(NettyMessage payload) {
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

    private void doConsume(NettyMessage message) {
        // LOG.info("doConsume text:{}, stamp:{}", message.getText(), message.getStamp());

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

    public abstract void onPorcess(String stamp, NettyPayload payload);

    @Override
    public final void onMessage(NettyMessage message) {
        add(message);
    }

}
