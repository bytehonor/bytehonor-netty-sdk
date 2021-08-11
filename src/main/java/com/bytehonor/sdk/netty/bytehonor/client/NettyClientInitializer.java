package com.bytehonor.sdk.netty.bytehonor.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // byte数组
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 4, 2, 0, 0));
        pipeline.addLast(new NettyClientByteHandler());
        // 字符串
        // ByteBuf buf = Unpooled.copiedBuffer(ProtocolConstants.END.getBytes(CharsetUtil.UTF_8));
        // pipeline.addLast(new DelimiterBasedFrameDecoder(2 * 1024, buf));
        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new NettyClientStringHandler());

    }
}
