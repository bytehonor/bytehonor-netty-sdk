package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeRequest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lijianqiang
 *
 */
public class NettyClient {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    private final NettyConfig config;
    private Bootstrap bootstrap;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this.config = NettyConfigBuilder.client(host, port).build();
    }
    
    public NettyClient(NettyConfig config) {
        this.config = config;
    }

    public void start() {
        LOG.info("Netty client start, host:{}, port, ssl:{}", config.getHost(), config.getPort(), config.isSsl());
        final EventLoopGroup group = new NioEventLoopGroup(config.getClientThreads());
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class); // 使用NioSocketChannel来作为连接用的channel类
        bootstrap.handler(new NettyClientInitializer(config));
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeoutMills());
        // 发起异步连接请求，绑定连接端口和host信息

        try {
            final ChannelFuture future = bootstrap.connect(config.getHost(), config.getPort()).sync();
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
        } catch (InterruptedException e) {
            LOG.error("connect error:{}", e.getMessage());
            throw new BytehonorNettySdkException(e);
        }

    }

    public Channel getChannel() {
        return channel;
    }

    public void ping() {
        NettyMessageSender.ping(channel);
    }

    public boolean isConnected() {
        return channel.isActive();
    }

    public void send(String value) {
        Objects.requireNonNull(value, "value");
        NettyMessageSender.send(channel, value);
    }

    public void subscribe(String names) {
        if (names == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.of(names));
    }

    public void unsubscribe(String names) {
        if (names == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.of(names, false));
    }
}
