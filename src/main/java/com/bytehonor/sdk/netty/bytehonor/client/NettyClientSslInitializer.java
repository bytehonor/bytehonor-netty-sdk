package com.bytehonor.sdk.netty.bytehonor.client;

import com.bytehonor.sdk.netty.bytehonor.common.util.NettySslUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;

public class NettyClientSslInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        SslContext sslContext = NettySslUtils.client();
        ChannelPipeline pipeline = ch.pipeline();
        // pipeline.addFirst("ssl", new SslHandler(sslEngine));
        pipeline.addFirst("ssl", sslContext.newHandler(ch.alloc()));
        // byte数组
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 4, 2, 0, 0));
        pipeline.addLast(new NettyClientByteHandler());

    }
}
