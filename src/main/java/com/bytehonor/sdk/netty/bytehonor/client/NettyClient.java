package com.bytehonor.sdk.netty.bytehonor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lijianqiang
 *
 */
public class NettyClient {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    private final String host;
    private final int port;
    private final NettyConfig config;
    private Bootstrap bootstrap;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this(host, port, new NettyConfig());
    }

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port, NettyConfig config) {
        this.host = host;
        this.port = port;
        this.config = config;
    }

    public void start() throws InterruptedException {
        LOG.info("Netty client start, host:{}, port, ssl", host, port, config.isSsl());
        final EventLoopGroup group = new NioEventLoopGroup(config.getClientThreads());
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class); // 使用NioSocketChannel来作为连接用的channel类
        bootstrap.handler(new NettyClientInitializer(config));
        // 发起异步连接请求，绑定连接端口和host信息
        ChannelFuture future = bootstrap.connect(host, port).sync();

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture arg0) throws Exception {
                if (future.isSuccess()) {
                    LOG.info("Netty client start success");
                } else {
                    LOG.error("Netty client start failed, error", future.cause());
                    group.shutdownGracefully(); // 关闭线程组
                }
            }
        });

        this.channel = future.channel();
    }

    public Channel getChannel() {
        return channel;
    }
}
