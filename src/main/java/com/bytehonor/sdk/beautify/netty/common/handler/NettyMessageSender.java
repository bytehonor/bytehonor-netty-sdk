package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.exception.NettyBeautifyException;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeRequest;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeResponse;
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

    public static void whoisClient(Channel channel, String whois) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(whois, "whois");

//        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.WHOIS_CLIENT, whois);
//        doSendBytes(channel, bytes);
    }

    public static void whoisServer(Channel channel, String whois) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(whois, "whois");

//        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.WHOIS_SERVER, whois);
//        doSendBytes(channel, bytes);
    }

    public static void subscribeResponse(Channel channel, SubscribeResponse response) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(response, "response");

        NettyPayload payload = NettyPayload.transfer(response);
//        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE_RESPONSE, payload.toString());
//        doSendBytes(channel, bytes);
    }

    public static void subscribeRequest(Channel channel, SubscribeRequest request) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(request, "request");

        NettyPayload payload = NettyPayload.transfer(request);
//        byte[] bytes = NettyDataUtils.build(NettyTypeEnum.SUBSCRIBE_REQUEST, payload.toString());
//        doSendBytes(channel, bytes);
    }

    public static void send(String stamp, NettyPayload payload) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(payload, "payload");

        doSendFrame(stamp, NettyFrame.of(payload));
    }

    private static void doSendFrame(String stamp, NettyFrame frame) {
        byte[] bytes = NettyDataUtils.build(frame.toString());
        Channel channel = StampChannelHolder.getChannel(stamp);
        if (channel == null) {
            throw new NettyBeautifyException("channel not exist, stamp:" + stamp);
        }

        if (channel.isActive() == false) {
            LOG.debug("send bytes failed, channelId:{} is not active", channel.id().asLongText());
            throw new NettyBeautifyException("channel is not active");
        }

        if (channel.isOpen() == false) {
            LOG.debug("send bytes failed, channelId:{} is not open", channel.id().asLongText());
            throw new NettyBeautifyException("channel is not open");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("send data:{}, channel:{}", NettyDataUtils.parseData(bytes), channel.id().asLongText());
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
