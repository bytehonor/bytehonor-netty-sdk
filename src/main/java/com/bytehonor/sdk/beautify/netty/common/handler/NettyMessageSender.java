package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.exception.NettyBeautifyException;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyMessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageSender.class);

    public static void ping(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        doSendFrame(stamp, NettyFrame.ping());
    }

    public static void pong(String stamp) {
        Objects.requireNonNull(stamp, "stamp");

        doSendFrame(stamp, NettyFrame.pong());
    }

    public static void send(String stamp, NettyPayload payload) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(payload, "payload");

        doSendFrame(stamp, NettyFrame.payload(payload));
    }

    private static void doSendFrame(String stamp, NettyFrame frame) {
        byte[] bytes = NettyDataUtils.build(frame.toString());
        Channel channel = StampChannelHolder.getChannel(stamp);
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

        if (LOG.isDebugEnabled()) {
            // LOG.debug("send data:{}, channel:{}", NettyDataUtils.parseData(bytes), stamp);
            LOG.debug("send method:{}, bytes:{}, channel:{}", frame.getMethod(), bytes.length, stamp);
        }

        ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
    }

    public static void pushGroup(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        Objects.requireNonNull(payload.getSubject(), "subject");

//        final String subject = payload.getSubject();
//        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, payload.toString());
//
//        Set<ChannelId> channelIds = SubjectChannelCacheHolder.list(subject);
//        for (ChannelId channelId : channelIds) {
//            Channel channel = ChannelCacheManager.getChannel(channelId);
//            if (channel == null) {
//                SubjectChannelCacheHolder.remove(subject, channelId);
//            }
//            try {
//                doSendBytes(channel, bytes);
//            } catch (Exception e) {
//                LOG.error("pushGroup name:{}, error", subject, e);
//            }
//        }
    }
}
