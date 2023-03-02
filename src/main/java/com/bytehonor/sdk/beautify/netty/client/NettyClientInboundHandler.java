package com.bytehonor.sdk.beautify.netty.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheManager;
import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyMessage;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

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

    private final String stamp;

    private final NettyClientHandler handler;

    public NettyClientInboundHandler(String stamp, NettyClientHandler handler) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(handler, "handler");

        this.stamp = stamp;
        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端上传消息
        Channel channel = ctx.channel();
        if (msg instanceof ByteBuf) {
//            NettyMessageReceiver.receiveByteBuf(channel, (ByteBuf) msg);
            onMessage((ByteBuf) msg);
        } else {
            String remoteAddress = channel.remoteAddress().toString();
            LOG.error("channelRead unknown msg:{}, remoteAddress:{}, channelId:{}", msg.toString(), remoteAddress,
                    channel.id().asLongText());
        }
    }

    private void onMessage(ByteBuf msg) {
        byte[] bytes = NettyDataUtils.readBytes(msg);
        NettyDataUtils.validate(bytes);
        String text = NettyDataUtils.parseData(bytes);
        handler.onMessage(NettyMessage.of(stamp, text));
    }

    // 当连接建立好的使用调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        onOpen(ctx.channel());
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
        handler.onClosed(stamp);
    }

    private void onOpen(Channel channel) {
        LOG.info("channelActive remoteAddress:{}, channelId:{}", channel.remoteAddress().toString(),
                channel.id().asLongText());
        StampChannelHolder.put(stamp, channel);
        ChannelCacheManager.add(channel);
        handler.onOpen(stamp);
    }
}
