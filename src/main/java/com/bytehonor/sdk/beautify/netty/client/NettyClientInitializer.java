package com.bytehonor.sdk.beautify.netty.client;

import com.bytehonor.sdk.beautify.netty.common.core.NettyIdleStateChecker;
import com.bytehonor.sdk.beautify.netty.common.core.NettyLengthFrameDecoder;
import com.bytehonor.sdk.beautify.netty.common.model.NettyClientConfig;
import com.bytehonor.sdk.beautify.netty.common.util.NettySslUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private final String stamp;

    private final NettyClientConfig config;

    private final NettyClientHandler handler;

    public NettyClientInitializer(String stamp, NettyClientConfig config, NettyClientHandler listener) {
        this.stamp = stamp;
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
//        pipeline.addLast(new IdleStateHandler(config.getReadIdleSeconds(), config.getWritIdleSeconds(),
//                config.getAllIdleSeconds()));
        pipeline.addLast(new NettyIdleStateChecker());

        // byte数组
        pipeline.addLast(new NettyLengthFrameDecoder());
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
