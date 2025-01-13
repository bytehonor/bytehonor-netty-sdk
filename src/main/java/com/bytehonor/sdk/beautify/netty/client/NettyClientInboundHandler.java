package com.bytehonor.sdk.beautify.netty.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessagePoolExecutor;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;

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
        onMessage(stamp, msg);
    }

    private void onMessage(String stamp, Object msg) {
        NettyMessagePoolExecutor.onMessage(stamp, msg, handler::onMessage);
    }

    // 当连接建立好的使用调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        onOpen(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        String remoteAddress = NettyChannelUtils.remoteAddress(channel);
        LOG.error("exceptionCaught remoteAddress:{}, stamp:{}, error", remoteAddress, stamp, cause);
        onClosed(channel, cause.getMessage());
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String remoteAddress = NettyChannelUtils.remoteAddress(channel);
        LOG.info("handlerRemoved remoteAddress:{}, stamp:{}", remoteAddress, stamp);
        onClosed(channel, "handlerRemoved");
    }

    private void onClosed(Channel channel, String msg) {
        ChannelCacheHolder.remove(channel);
        StampChannelHolder.remove(stamp);
        handler.onClosed(stamp, msg);
    }

    private void onOpen(Channel channel) {
        LOG.info("channelActive remoteAddress:{}, stamp:{}", channel.remoteAddress().toString(), stamp);
        ChannelCacheHolder.add(channel);
        StampChannelHolder.put(stamp, channel);
        handler.onOpen(stamp);
    }
}
