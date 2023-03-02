package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.listener.DefaultNettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyClientHandler;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfig;
import com.bytehonor.sdk.beautify.netty.common.model.NettyConfigBuilder;
import com.bytehonor.sdk.beautify.netty.common.util.NettyStampGenerator;

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

    private final String stamp;
    private final NettyConfig config;
    private final NettyClientHandler handler;
    private final Bootstrap bootstrap;
    private Channel channel;

    // 连接服务端的端口号地址和端口号
    public NettyClient(String host, int port, NettyClientHandler listener) {
        this(NettyConfigBuilder.client(host, port).build(), listener);
    }

    public NettyClient(String host, int port) {
        this(host, port, new DefaultNettyClientHandler());
    }

    public NettyClient(NettyConfig config, NettyClientHandler handler) {
        this.stamp = NettyStampGenerator.stamp();
        this.config = config;
        this.handler = handler;
        this.bootstrap = init();
    }

    public NettyClient(NettyConfig config) {
        this(config, new DefaultNettyClientHandler());
    }

    private Bootstrap init() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup(config.getClientThreads());
        bootstrap.group(group).channel(NioSocketChannel.class); // 使用NioSocketChannel来作为连接用的channel类
        bootstrap.handler(new NettyClientInitializer(stamp, config, handler));
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeoutMills());
        return bootstrap;
    }

    public void start() {
        LOG.info("Netty client start, stamp:{}, host:{}, port:{}", stamp, config.getHost(), config.getPort());
        // 发起异步连接请求，绑定连接端口和host信息
        try {
            final ChannelFuture future = bootstrap.connect(config.getHost(), config.getPort()).sync();
            channel = future.channel();
            future.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture arg0) throws Exception {
                    if (future.isSuccess()) {
                        LOG.info("Netty client start success, stamp:{}", stamp);
                    } else {
                        LOG.error("Netty client start failed, stamp:{}, cause", stamp, future.cause());
//                        group.shutdownGracefully(); // 关闭线程组
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Netty client start error, stamp:{}, error", stamp, e);
            handler.onError(stamp, e);
        }
    }

//    public Channel getChannel() {
//        return channel;
//    }
//
//    public void ping() {
//        NettyMessageSender.ping(channel);
//    }
//
//    public boolean isConnected() {
//        return channel.isActive();
//    }
//
//    public void send(NettyPayload payload) {
//        Objects.requireNonNull(payload, "payload");
////        NettyMessageSender.send(channel, payload);
//    }
//
//    public void subscribe(String subject) {
//        if (subject == null) {
//            return;
//        }
//        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.yes(subject));
//    }
//
//    public void unsubscribe(String subject) {
//        if (subject == null) {
//            return;
//        }
//        NettyMessageSender.subscribeRequest(channel, SubscribeRequest.no(subject));
//    }

    public void close() {
        if (channel == null) {
            return;
        }
        channel.close();
        LOG.info("Netty client close, stamp:{}", stamp);
    }
}
