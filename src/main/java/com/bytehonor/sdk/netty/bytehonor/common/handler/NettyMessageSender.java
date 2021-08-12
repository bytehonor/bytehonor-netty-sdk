package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyMessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageSender.class);

    private static final byte[] PING = "ping".getBytes();

    public static void ping(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.HEART.getType(), PING);
        send(channel, bytes);
    }

    public static void send(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        byte[] bytes = NettyDataUtils.build(value);
        send(channel, bytes);
    }

    public static void send(Channel channel, byte[] bytes) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(bytes, "bytes");

        if (LOG.isDebugEnabled()) {
            String channelId = channel.id().asLongText();
            LOG.debug("send bytes:({}), channelId:{}", bytes, channelId);
        }

        ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
    }

    public static void broadcast(String value) {
        Objects.requireNonNull(value, "value");
        final byte[] bytes = NettyDataUtils.build(value);
        ServerChannelHolder.stream().forEach(channel -> {
            send(channel, bytes);
        });
    }
}
