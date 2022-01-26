package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.SubscribeCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.WhoiamHolder;
import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeRequest;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeResponse;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

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

    public static void whoisClient(Channel channel, String whois) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(whois, "whois");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.WHOIS_CLIENT, whois);
        doSendBytes(channel, bytes);
    }

    public static void whoisServer(Channel channel, String whois) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(whois, "whois");

        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.WHOIS_SERVER, whois);
        doSendBytes(channel, bytes);
    }

    public static void subscribeResponse(Channel channel, SubscribeResponse response) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(response, "response");

        NettyPayload payload = NettyPayload.fromOne(response);
        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE_RESPONSE, payload.toString());
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

        payload.setWhois(WhoiamHolder.getWhoiam());
        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, payload.toString());
        doSendBytes(channel, bytes);
    }

//    public static void send(Channel channel, String value) {
//        Objects.requireNonNull(channel, "channel");
//        Objects.requireNonNull(value, "value");
//
//        send(channel, NettyPayload.fromOne(value));
//    }

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

    public static void broadcast(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        Objects.requireNonNull(payload.getSubject(), "subject");

        payload.setWhois(WhoiamHolder.getWhoiam());
        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, payload.toString());
        ChannelCacheHolder.parallelStream().forEach(channel -> {
            if (channel.isActive() == false) {
                return;
            }
            doSendBytes(channel, bytes);
        });
    }

    public static void pushGroup(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        Objects.requireNonNull(payload.getSubject(), "subject");

        payload.setWhois(WhoiamHolder.getWhoiam());
        final String subject = payload.getSubject();
        final byte[] bytes = NettyDataUtils.build(NettyTypeEnum.PUBLIC_PAYLOAD, payload.toString());

        List<ChannelId> channelIds = SubscribeCacheHolder.get(subject);
        for (ChannelId id : channelIds) {
            Channel channel = ChannelCacheHolder.get(id);
            if (channel == null) {
                SubscribeCacheHolder.remove(subject, id);
            }
            try {
                doSendBytes(channel, bytes);
            } catch (Exception e) {
                LOG.error("pushGroup name:{}, error", subject, e);
            }
        }
    }
}
