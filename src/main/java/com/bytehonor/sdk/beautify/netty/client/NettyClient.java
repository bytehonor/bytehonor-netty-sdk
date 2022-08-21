package com.bytehonor.sdk.beautify.netty.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.handler.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientListener;
import com.bytehonor.sdk.beautify.netty.common.listener.ClientListenerHelper;
import com.bytehonor.sdk.beautify.netty.common.listener.DefaultNettyClientListener;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeRequest;

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
    private final NettyClientListener listener;
    private Bootstrap bootstrap;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port, NettyClientListener listener) {
        this(NettyConfigBuilder.client(host, port).build(), listener);
    }

    public NettyClient(String host, int port) {
        this(host, port, new DefaultNettyClientListener());
    }

    public NettyClient(NettyConfig config, NettyClientListener listener) {
        this.config = config;
        this.listener = listener != null ? listener : new DefaultNettyClientListener();
    }

    public NettyClient(NettyConfig config) {
        this(config, new DefaultNettyClientListener());
    }

    public void start() {
        LOG.info("Netty client start, host:{}, port:{}", config.getHost(), config.getPort());
        final EventLoopGroup group = new NioEventLoopGroup(config.getClientThreads());
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class); // 使用NioSocketChannel来作为连接用的channel类
        bootstrap.handler(new NettyClientInitializer(config, listener));
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
                        LOG.error("Netty client start failed, cause", future.cause());
                        group.shutdownGracefully(); // 关闭线程组
                    }
                }
            });
            this.channel = future.channel();
        } catch (Exception e) {
            LOG.error("connect ({}:{}) error:{}", config.getHost(), config.getPort(), e.getMessage());
            ClientListenerHelper.onError(listener, e);
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

    public void subscribe(String subject) {
        if (subject == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.yes(subject));
    }

    public void unsubscribe(String subject) {
        if (subject == null) {
            return;
        }
        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.no(subject));
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
