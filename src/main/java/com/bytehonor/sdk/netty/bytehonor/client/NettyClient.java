package com.bytehonor.sdk.netty.bytehonor.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.cache.WhoiamHolder;
import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;
import com.bytehonor.sdk.netty.bytehonor.common.listener.DefaultNettyListener;
import com.bytehonor.sdk.netty.bytehonor.common.listener.NettyListener;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfig;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeRequest;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyListenerUtils;

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
    private final NettyListener listener;
    private Bootstrap bootstrap;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port, NettyListener listener) {
        this(NettyConfigBuilder.client(host, port).build(), listener);
    }

    public NettyClient(String host, int port) {
        this(host, port, new DefaultNettyListener());
    }

    public NettyClient(NettyConfig config, NettyListener listener) {
        this.config = config;
        this.listener = listener != null ? listener : new DefaultNettyListener();
    }

    public NettyClient(NettyConfig config) {
        this(config, new DefaultNettyListener());
    }

    public void start() {
        WhoiamHolder.setWhoiam(config.getWhoiam());
        LOG.info("Netty client start, host:{}, port:{}", config.getHost(), config.getPort());
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
                        NettyListenerUtils.onOpen(listener, future.channel());
                    } else {
                        LOG.error("Netty client start failed, cause", future.cause());
                        group.shutdownGracefully(); // 关闭线程组
                        NettyListenerUtils.onClosed(listener, future.cause().getMessage());
                    }
                }
            });
            this.channel = future.channel();
        } catch (Exception e) {
            LOG.error("connect ({}:{}) error:{}", config.getHost(), config.getPort(), e.getMessage());
            NettyListenerUtils.onError(listener, e);
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

    public void send(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");
        NettyMessageSender.send(channel, payload);
    }

    public void subscribe(String subjects) {
        if (subjects == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.of(subjects));
    }

    public void unsubscribe(String subjects) {
        if (subjects == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.of(subjects, false));
    }
    
    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
