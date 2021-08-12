package com.bytehonor.sdk.netty.bytehonor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.ServerChannelHolder;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyServerStringHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerStringHandler.class);

    /**
     * 
     * @param ctx 可以拿到本次处理的上下文信息
     * @param msg 就是获取到的信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        try {
            NettyMessageReceiver.receiveString(channel, msg);
        } catch (Exception e) {
            LOG.error("channelRead0 msg:{}, error", msg, e);
        }
    }

    /**
     * 
     * @param ctx   当前的应用上下文
     * @param cause Throwable是异常和Error的顶级接口,此处就是异常
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 有异常就关闭连接
        Channel channel = ctx.channel();
        LOG.error("exceptionCaught channelId:{}, error", channel.id().asLongText(), cause);
        ServerChannelHolder.remove(channel);
        ctx.close();
    }

    /**
     * 当客户连接服务端之后（打开链接） 获取客户端的channel，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        LOG.info("handlerAdded channelId:{}", channelId);

        ServerChannelHolder.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        LOG.info("handlerRemoved channelId:{}", channel.id().asLongText());

        // 当触发handlerRemoved
        ServerChannelHolder.remove(channel);
    }

}
