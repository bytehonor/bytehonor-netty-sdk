package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.SubscribeChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeRequest;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeResult;
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
        doSendBytes(channel, bytes);
    }

    public static void pong(Channel channel) {
        Objects.requireNonNull(channel, "channel");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PONG, PONG);
        doSendBytes(channel, bytes);
    }

    public static void subscribeResult(Channel channel, SubscribeResult result) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(result, "result");

        NettyPayload payload = NettyPayload.fromOne(result);
        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE_RESULT, payload.toString());
        doSendBytes(channel, bytes);
    }

    public static void subscribeRequest(Channel channel, SubscribeRequest request) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(request, "request");

        NettyPayload payload = NettyPayload.fromOne(request);
        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE_REQUEST, payload.toString());
        doSendBytes(channel, bytes);
    }

    public static void send(Channel channel, NettyPayload payload) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(payload, "payload");

        send(channel, payload.toString());
    }

    public static void send(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, value);
        doSendBytes(channel, bytes);
    }

    private static void doSendBytes(Channel channel, byte[] bytes) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(bytes, "bytes");

        if (channel.isActive() == false) {
            LOG.debug("send bytes failed, channelId:{} is not active", channel.id().asLongText());
            throw new BytehonorNettySdkException("channel is not active");
        }

        if (channel.isOpen() == false) {
            LOG.debug("send bytes failed, channelId:{} is not open", channel.id().asLongText());
            throw new BytehonorNettySdkException("channel is not open");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("send data:{}, channelId:{}", NettyDataUtils.parseData(bytes), channel.id().asLongText());
        }

        ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
    }

    public static void broadcast(String value) {
        Objects.requireNonNull(value, "value");

        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, value);
        ServerChannelHolder.parallelStream().forEach(channel -> {
            if (channel.isActive() == false) {
                return;
            }
            doSendBytes(channel, bytes);
        });
    }

    public static void pushGroup(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        Objects.requireNonNull(payload.getName(), "name");

        final String name = payload.getName();
        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, payload.toString());
        ServerChannelHolder.parallelStream().forEach(channel -> {
            if (channel.isActive() == false) {
                return;
            }
            String key = SubscribeChannelHolder.makeKey(channel.id(), name);
            if (SubscribeChannelHolder.exists(key) == false) {
                return;
            }
            doSendBytes(channel, bytes);
        });
    }
}
