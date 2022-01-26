package com.bytehonor.sdk.netty.bytehonor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lijianqiang
 *
 */
public class NettyServerByteHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerByteHandler.class);

    private String whois;

    public NettyServerByteHandler() {
        this(null);
    }

    public NettyServerByteHandler(String whois) {
        this.whois = whois;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端上传消息
        Channel channel = ctx.channel();
        if (msg instanceof ByteBuf) {
            NettyMessageReceiver.receiveByteBuf(channel, (ByteBuf) msg);
        } else {
            String remoteAddress = channel.remoteAddress().toString();
            LOG.error("channelRead unknown msg:{}, remoteAddress:{}, channelId:{}", msg.toString(), remoteAddress,
                    channel.id().asLongText());
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
        String remoteAddress = channel.remoteAddress().toString();
        LOG.error("exceptionCaught remoteAddress:{}, channelId:{}, error", remoteAddress, channel.id().asLongText(),
                cause);
        ChannelCacheHolder.remove(channel);
        ctx.close();
    }

    /**
     * 当客户连接服务端之后（打开链接） 获取客户端的channel，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (this.whois != null) {
            NettyMessageSender.whoisServer(channel, this.whois);
        }
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("handlerAdded whois:{}, remoteAddress:{}, channelId:{}", whois, remoteAddress,
                channel.id().asLongText());
        // 缓存连接
        ChannelCacheHolder.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("handlerRemoved remoteAddress:{}, channelId:{}", remoteAddress, channel.id().asLongText());

        // 当触发handlerRemoved
        ChannelCacheHolder.remove(channel);
    }

}
