package com.bytehonor.sdk.beautify.netty.server;

import com.bytehonor.sdk.beautify.netty.common.core.NettyIdleStateChecker;
import com.bytehonor.sdk.beautify.netty.common.core.NettyLengthFrameDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author lijianqiang
 *
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyServerHandler handler;

    public NettyServerInitializer(NettyServerHandler handler) {
        this.handler = handler;
    }

    // ch就可以对channel进行设置
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // ChannelPipeline类是ChannelHandler实例对象的链表，用于处理或截获通道的接收和发送数据
        ChannelPipeline pipeline = ch.pipeline();
        // 也可以选择将处理器加到pipeLine的那个位置

        // 自定义的空闲检测
//        pipeline.addLast(new NettyIdleStateChecker(config.getReadIdleSeconds(), config.getWritIdleSeconds(),
//                config.getAllIdleSeconds()));
        pipeline.addLast(new NettyIdleStateChecker());

        // byte数组写法， 一些限定和编码解码器
        pipeline.addLast(new NettyLengthFrameDecoder());
        pipeline.addLast(new NettyServerInboundHandler(handler));

        // 字符串写法， 处理客户端连续发送流导致粘包问题，客户端发送的信息需要END代表发生结束
        // ByteBuf buf =
        // Unpooled.copiedBuffer(ProtocolConstants.END.getBytes(CharsetUtil.UTF_8));
        // pipeline.addLast(new DelimiterBasedFrameDecoder(2 * 1024, buf));
        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new NettyServerStringHandler());

        pipeline.addLast(new NettyServerHeartBeatHandler());
    }
}
