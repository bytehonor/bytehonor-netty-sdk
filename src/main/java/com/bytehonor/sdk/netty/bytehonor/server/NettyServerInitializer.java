package com.bytehonor.sdk.netty.bytehonor.server;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    
    // ch就可以对channel进行设置
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // ChannelPipeline类是ChannelHandler实例对象的链表，用于处理或截获通道的接收和发送数据
        ChannelPipeline pipeline = ch.pipeline();
        // 也可以选择将处理器加到pipeLine的那个位置
        // byte数组写法， 一些限定和编码解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 4, 2, 0, 0));
        pipeline.addLast(new NettyServerByteHandler());

        // 字符串写法， 处理客户端连续发送流导致粘包问题，客户端发送的信息需要END代表发生结束
        // ByteBuf buf =
        // Unpooled.copiedBuffer(ProtocolConstants.END.getBytes(CharsetUtil.UTF_8));
        // pipeline.addLast(new DelimiterBasedFrameDecoder(2 * 1024, buf));
        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new NettyServerStringHandler());

        // 自定义的空闲检测
        pipeline.addLast(new IdleStateHandler(5, 5, NettyConstants.CLIENT_HEART_TIMEOUT_SECONDS));
        pipeline.addLast(new NettyHeartBeatHandler());
    }
}
