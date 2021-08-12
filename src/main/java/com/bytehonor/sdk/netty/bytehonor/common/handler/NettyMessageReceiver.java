package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.util.NettyDataUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettyMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    public static void receiveByteBuf(Channel channel, ByteBuf buf) {
        try {
            byte[] bytes = NettyDataUtils.readBytes(buf);

            NettyDataUtils.validate(bytes);

            int type = bytes[1];
            String message = NettyDataUtils.parseData(bytes);
            NettyHandler handler = NettyHandlerFactory.get(type);
            if (handler == null) {
                LOG.warn("receive unknown type:{}, message:{}, channelId:{}", type, message, channel.id().asLongText());
                return;
            }
            handler.handle(channel, message);
        } catch (Exception e) {
            LOG.error("receiveByteBuf, channel:{}, error", channel.id().asLongText(), e);
        }

    }

    public static void receiveString(Channel channel, String msg) {
        throw new RuntimeException("TODO");
    }
}
