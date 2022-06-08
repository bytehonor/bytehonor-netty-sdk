package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageReceiver;
import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.listener.ClientListener;
import com.bytehonor.sdk.beautify.netty.common.listener.ClientListenerHelper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lijianqiang
 *
 */
public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientInboundHandler.class);

    private final String whoiam;

    private final ClientListener listener;

    public NettyClientInboundHandler(String whoiam, ClientListener listener) {
        this.whoiam = whoiam != null ? whoiam : "unknown";
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

    // 当连接建立好的使用调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        onOpen(channel);
        LOG.info("channelActive remoteAddress:{}, channelId:{}", channel.remoteAddress().toString(),
                channel.id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        onClosed(channel);
        LOG.error("exceptionCaught remoteAddress:{}, channelId:{}, error", channel.remoteAddress().toString(),
                channel.id().asLongText(), cause);
        ctx.close();
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        onClosed(channel);
        String remoteAddress = channel.remoteAddress().toString();
        LOG.info("handlerRemoved remoteAddress:{}, channelId:{}", remoteAddress, channel.id().asLongText());
    }

    private void onClosed(Channel channel) {
        ChannelCacheManager.remove(channel);
        ClientListenerHelper.onClosed(listener, "channel close");
    }

    private void onOpen(Channel channel) {
        ChannelCacheManager.add(channel);
        NettyMessageSender.whoisClient(channel, whoiam);
        ClientListenerHelper.onOpen(listener, channel);
    }
}
