package com.bytehonor.sdk.netty.bytehonor.client;

import com.bytehonor.sdk.netty.bytehonor.common.listener.ClientListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettySslUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * @author lijianqiang
 *
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private NettyConfig config;

    private ClientListener listener;

    public NettyClientInitializer(NettyConfig config, ClientListener listener) {
        this.config = config;
        this.listener = listener;
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
        // byte数组
        pipeline.addLast(new LengthFieldBasedFrameDecoder(config.getMaxFrameLength(), config.getLengthFieldOffset(),
                config.getLengthFieldLength(), 0, 0));
        pipeline.addLast(new NettyClientInboundHandler(config.getWhoiam(), listener));

        // 自定义的空闲检测
        // pipeline.addLast(new IdleStateHandler(config.getReadIdleTimeSeconds(),
        // config.getWritIdleTimeSeconds(),
        // config.getAllIdleTimeSeconds()));
        // 字符串
        // ByteBuf buf =
        // Unpooled.copiedBuffer(ProtocolConstants.END.getBytes(CharsetUtil.UTF_8));
        // pipeline.addLast(new DelimiterBasedFrameDecoder(2 * 1024, buf));
        // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // pipeline.addLast(new NettyClientStringHandler());

    }
}
