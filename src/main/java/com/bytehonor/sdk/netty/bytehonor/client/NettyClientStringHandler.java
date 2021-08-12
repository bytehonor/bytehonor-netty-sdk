package com.bytehonor.sdk.netty.bytehonor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyClientStringHandler extends SimpleChannelInboundHandler<String> {
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyClientStringHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        NettyMessageReceiver.receiveString(channel, msg);
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
