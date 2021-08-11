package com.bytehonor.sdk.netty.bytehonor.server;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettySslUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServerSslInitializer extends ChannelInitializer<SocketChannel> {

    // ch就可以对channel进行设置
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        SslContext sslContext = NettySslUtils.server();

        // ChannelPipeline类是ChannelHandler实例对象的链表，用于处理或截获通道的接收和发送数据
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addFirst("ssl", sslContext.newHandler(ch.alloc()));

        // 也可以选择将处理器加到pipeLine的那个位置
        // byte数组写法， 一些限定和编码解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstants.MAX_LENGTH, NettyConstants.LENGTH_OFFSET,
                NettyConstants.LENGTH_SIZE, 0, 0));
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
