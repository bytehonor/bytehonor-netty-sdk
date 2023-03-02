package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfig;
import com.bytehonor.sdk.beautify.netty.common.util.NettySslUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private String stamp;
    
    private NettyConfig config;

    private NettyClientHandler handler;

    public NettyClientInitializer(String stamp, NettyConfig config, NettyClientHandler listener) {
        this.config = config;
        this.handler = listener;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (config.isSsl()) {
            if (config.isSslEngine()) {
                pipeline.addFirst("ssl", new SslHandler(NettySslUtils.clientSsl(config.getSslPassword())));
            } else {
                SslContext sslContext = NettySslUtils.client(config.getSslPassword());
                pipeline.addFirst("ssl", sslContext.newHandler(ch.alloc()));
            }
        }

        // 自定义的空闲检测
        pipeline.addLast(new IdleStateHandler(config.getReadIdleSeconds(), config.getWritIdleSeconds(),
                config.getAllIdleSeconds()));

        // byte数组
        pipeline.addLast(new LengthFieldBasedFrameDecoder(config.getMaxFrameLength(), config.getLengthFieldOffset(),
                config.getLengthFieldLength(), 0, 0));
        pipeline.addLast(new NettyClientInboundHandler(stamp, handler));

        // 字符串
        // ByteBuf buf =
        // Unpooled.copiedBuffer(ProtocolConstants.END.getBytes(CharsetUtil.UTF_8));
        // pipeline.addLast(new DelimiterBasedFrameDecoder(2 * 1024, buf));
        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new NettyClientHeartBeatHandler());

    }
}
