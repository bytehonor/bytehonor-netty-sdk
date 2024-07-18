package com.bytehonor.sdk.beautify.netty.server;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;
import com.bytehonor.sdk.beautify.netty.common.util.NettyDataUtils;

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

    private final NettyServerHandler handler;

    public NettyServerInboundHandler(NettyServerHandler handler) {
        Objects.requireNonNull(handler, "handler");

        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端上传消息
        Channel channel = ctx.channel();
        String stamp = NettyChannelUtils.stamp(channel);
        if (msg instanceof ByteBuf) {
            onMessage(stamp, (ByteBuf) msg);
        } else {
            String remoteAddress = NettyChannelUtils.remoteAddress(channel);
            LOG.error("channelRead unknown msg:{}, remoteAddress:{}, stamp:{}", msg.getClass().getSimpleName(),
                    remoteAddress, stamp);
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
        String remoteAddress = NettyChannelUtils.remoteAddress(channel);
        String stamp = NettyChannelUtils.stamp(channel);
        LOG.error("exceptionCaught remoteAddress:{}, stamp:{}, error", remoteAddress, stamp, cause);
        onDisconnected(channel, remoteAddress, stamp);
        ctx.close();
    }

    /**
     * 当客户连接服务端之后（打开链接） 获取客户端的channel，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String remoteAddress = NettyChannelUtils.remoteAddress(channel);
        String stamp = NettyChannelUtils.stamp(channel);
        LOG.info("handlerAdded remoteAddress:{}, channelId:{}", remoteAddress, channel.id().asLongText());
        onConnected(channel, remoteAddress, stamp);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String remoteAddress = NettyChannelUtils.remoteAddress(channel);
        String stamp = NettyChannelUtils.stamp(channel);
        onDisconnected(channel, remoteAddress, stamp);
    }

    private void onMessage(String stamp, ByteBuf msg) {
        try {
            byte[] bytes = NettyDataUtils.readBytes(msg);
            NettyDataUtils.validate(bytes);
            String text = NettyDataUtils.parseData(bytes);
            handler.onMessage(stamp, text);
        } catch (Exception e) {
            LOG.error("onMessage stamp:{}, error", stamp, e);
        }
    }

    private void onDisconnected(Channel channel, String remoteAddress, String stamp) {
        LOG.info("onDisconnected remoteAddress:{}, stamp:{}", remoteAddress, stamp);

        ChannelCacheHolder.remove(channel);
        StampChannelHolder.remove(stamp);
        handler.onDisconnected(stamp);
    }

    private void onConnected(Channel channel, String remoteAddress, String stamp) {
        LOG.info("onConnected remoteAddress:{}, stamp:{}", remoteAddress, stamp);

        ChannelCacheHolder.add(channel);
        StampChannelHolder.put(stamp, channel);
        handler.onConnected(stamp);
    }

}
