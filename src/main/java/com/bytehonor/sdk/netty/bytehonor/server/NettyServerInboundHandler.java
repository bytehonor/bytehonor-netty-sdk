package com.bytehonor.sdk.netty.bytehonor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.WhoiamHolder;
import com.bytehonor.sdk.netty.bytehonor.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageReceiver;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;
import com.bytehonor.sdk.netty.bytehonor.common.listener.ServerListenerHelper;
import com.bytehonor.sdk.netty.bytehonor.common.listener.ServerListener;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lijianqiang
 *
 */
public class NettyServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerInboundHandler.class);

    private ServerListener listener;

    public NettyServerInboundHandler(ServerListener listener) {
        this.listener = listener;
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
        Channel channel = ctx.channel();
        onRemoved(channel);
        LOG.error("exceptionCaught remoteAddress:{}, channelId:{}, error", channel.remoteAddress().toString(),
                channel.id().asLongText(), cause);
        ctx.close();
    }

    /**
     * 当客户连接服务端之后（打开链接） 获取客户端的channel，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        onAdded(channel);
        LOG.info("handlerAdded remoteAddress:{}, channelId:{}", channel.remoteAddress().toString(),
                channel.id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        onRemoved(channel);
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("handlerRemoved remoteAddress:{}, channelId:{}", remoteAddress, channel.id().asLongText());
    }

    private void onRemoved(Channel channel) {
        ChannelCacheManager.remove(channel);
        ServerListenerHelper.onTotal(listener, ChannelCacheManager.size());
    }

    private void onAdded(Channel channel) {
        ChannelCacheManager.add(channel);
        NettyMessageSender.whoisServer(channel, WhoiamHolder.whoiam());
        ServerListenerHelper.onTotal(listener, ChannelCacheManager.size());
    }
}
