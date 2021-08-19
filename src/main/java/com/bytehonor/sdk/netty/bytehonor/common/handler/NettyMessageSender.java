package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
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

    private static final String PING = "ping";

    private static final String PONG = "pong";

    public static void ping(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PING, PING);
        send(channel, bytes);
    }

    public static void pong(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PONG, PONG);
        send(channel, bytes);
    }

    public static void accept(Channel channel, boolean success) {
        Objects.requireNonNull(channel, "channel");
        String data = success ? "true" : "false";
        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.ACCEPT, data);
        send(channel, bytes);
    }

    public static void subscribe(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE, value);
        send(channel, bytes);
    }

    public static void unsubscribe(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.UNSUBSCRIBE, value);
        send(channel, bytes);
    }

    public static void send(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        byte[] bytes = NettyDataUtils.build(value);
        send(channel, bytes);
    }

    private static void send(Channel channel, byte[] bytes) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(bytes, "bytes");

        if (channel.isActive() == false) {
            LOG.debug("send bytes:{} failed, channelId:{} is not active", bytes, channel.id().asLongText());
            throw new BytehonorNettySdkException("channel is not active");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("send bytes:{}, data:{}, channelId:{}", bytes, NettyDataUtils.parseData(bytes),
                    channel.id().asLongText());
        }

        ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
    }

    public static void broadcast(String value) {
        Objects.requireNonNull(value, "value");

        final byte[] bytes = NettyDataUtils.build(value);
        ServerChannelHolder.parallelStream().forEach(channel -> {
            if (channel.isActive() == false) {
                return;
            }
            send(channel, bytes);
        });
    }

    public static void push(String category, String value) {
        Objects.requireNonNull(category, "category");
        Objects.requireNonNull(value, "value");

        final byte[] bytes = NettyDataUtils.build(value);
        ServerChannelHolder.parallelStream().forEach(channel -> {
            if (channel.isActive() == false) {
                return;
            }
            String key = SubscribeChannelHolder.makeKey(channel.id(), category);
            if (SubscribeChannelHolder.exists(key) == false) {
                return;
            }
            send(channel, bytes);
        });
    }
}
