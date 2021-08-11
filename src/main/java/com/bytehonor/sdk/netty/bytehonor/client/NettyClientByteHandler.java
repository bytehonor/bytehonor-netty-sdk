package com.bytehonor.sdk.netty.bytehonor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientByteHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientByteHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端上传消息
        Channel channel = ctx.channel();
        if (msg instanceof ByteBuf) {
            NettyMessageReceiver.receiveByteBuf(channel, (ByteBuf) msg);
        } else {
            LOG.error("channelRead unknown msg:{}, channelId:{}", msg.toString(), channel.id().asLongText());
        }
    }

    // 当连接建立好的使用调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channelActive channelId:{} sendId", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("exceptionCaught error", cause);
        ctx.close();
    }
}
