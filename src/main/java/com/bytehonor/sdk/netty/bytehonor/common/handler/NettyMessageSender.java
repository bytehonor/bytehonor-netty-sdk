package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.util.NettyByteUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class NettyMessageSender {
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    public static boolean sendByte = true;

    public static void send(Channel channel, String value) {
        Objects.requireNonNull(channel, "channel");
        Objects.requireNonNull(value, "value");

        String channelId = channel.id().asLongText();
        byte[] bytes = NettyByteUtils.hexStringToBytes(value);
        LOG.info("send sendByte:{}, cmd:({}), bytes:({}), channelId:{}", sendByte, value, bytes, channelId);

        if (sendByte) {
            ByteBuf buf = Unpooled.buffer();// netty需要用ByteBuf传输
            buf.writeBytes(bytes);// 对接需要16进制
            channel.writeAndFlush(buf);
        } else {
            channel.writeAndFlush(value);
        }
    }
}
