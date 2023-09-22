package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.exception.NettyBeautifyException;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.NettySendMission;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * 独立线程，且单例
 * 
 * @author lijianqiang
 *
 */
public final class NettyMessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageSender.class);

    /**
     * 队列
     */
    private final LinkedBlockingQueue<NettySendMission> queue;
    /**
     * 线程
     */
    private final Thread thread;

    private NettyMessageSender() {
        queue = new LinkedBlockingQueue<NettySendMission>(40960);

        thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                while (true) {
                    try {
                        // 从队列中取值,如果没有对象过期则队列一直等待，
                        NettySendMission mission = queue.take();
                        doSendFrame(mission.getStamp(), mission.getFrame());
                    } catch (Exception e) {
                        LOG.error("runInSafe error", e);
                    }
                }
            }

        });

        thread.setName(NettyMessageSender.class.getSimpleName());
        thread.start();
        LOG.info("[Thread] {} start", thread.getName());
    }

    private void doAdd(String stamp, NettyFrame frame) {
        this.queue.add(NettySendMission.of(stamp, frame));
    }

    private static class LazyHolder {
        private static NettyMessageSender SINGLE = new NettyMessageSender();
    }

    private static NettyMessageSender self() {
        return LazyHolder.SINGLE;
    }

    public static void ping(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        // 不能放队列里, 抛异常上层捕获不到则无法重连
        doSendFrame(stamp, NettyFrame.ping());
    }

    public static void pong(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        // 不能放队列里
        doSendFrame(stamp, NettyFrame.pong());
    }

    public static void send(String stamp, NettyPayload payload) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(payload, "payload");

        doSendFrame(stamp, NettyFrame.payload(payload));
    }

    public static void add(String stamp, NettyPayload payload) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(payload, "payload");

        self().doAdd(stamp, NettyFrame.payload(payload));
    }

    private static void doSendFrame(String stamp, NettyFrame frame) {
        Channel channel = StampChannelHolder.get(stamp);
        if (channel == null) {
            throw new NettyBeautifyException("channel not exist, stamp:" + stamp);
        }

        if (channel.isActive() == false) {
            LOG.debug("send bytes failed, stamp:{} is not active", stamp);
            throw new NettyBeautifyException("channel is not active");
        }

        if (channel.isOpen() == false) {
            LOG.debug("send bytes failed, stamp:{} is not open", stamp);
            throw new NettyBeautifyException("channel is not open");
        }

        byte[] bytes = NettyDataUtils.build(frame.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug("send method:{}, bytes:{}, channel:{}", frame.getMethod(), bytes.length, stamp);
        }

        ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
    }
}
