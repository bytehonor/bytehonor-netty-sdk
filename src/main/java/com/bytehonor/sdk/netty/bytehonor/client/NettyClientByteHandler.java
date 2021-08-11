package com.bytehonor.sdk.netty.bytehonor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyByteUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientByteHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端上传消息
        Channel channel = ctx.channel();
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes); // 复制内容到字节数组bytes
            String message = NettyByteUtils.bytesToHexStrings(bytes);
            // String message1 = new String(bytes);
            LOG.info("channelRead message:{}, channelId:{}", message, channel.id().asLongText());
            NettyMessageReceiver.receive(channel, message);
        } else {
            LOG.error("channelRead msg:{}, channelId:{}", msg, channel.id().asLongText());
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
