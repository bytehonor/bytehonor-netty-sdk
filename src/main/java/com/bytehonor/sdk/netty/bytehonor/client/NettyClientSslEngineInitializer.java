package com.bytehonor.sdk.netty.bytehonor.client;

import javax.net.ssl.SSLEngine;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;

public class NettyClientSslEngineInitializer extends ChannelInitializer<SocketChannel> {

    private SSLEngine sslEngine;

    public NettyClientSslEngineInitializer(SSLEngine sslEngine) {
        this.sslEngine = sslEngine;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addFirst("ssl", new SslHandler(sslEngine));
        // byte数组
        pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstants.MAX_LENGTH,
                NettyConstants.LENGTH_OFFSET, NettyConstants.LENGTH_SIZE, 0, 0));
        pipeline.addLast(new NettyClientByteHandler());

    }
}
