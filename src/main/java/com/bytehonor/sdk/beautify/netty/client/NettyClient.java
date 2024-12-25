package com.bytehonor.sdk.beautify.netty.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.core.NettyMessageSender;
import com.bytehonor.sdk.beautify.netty.common.model.NettyClientConfig;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTaskScheduler;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;

import io.netty.bootstrap.Bootstrap;
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
    private final NettyClientConfig config;
    private final NettyClientHandler handler;
    private final Bootstrap bootstrap;

    // 连接服务端的端口号地址和端口号
    private NettyClient(NettyClientConfig config, NettyClientHandler handler) {
        this.stamp = NettyChannelUtils.stamp(config.getHost(), config.getPort());
        this.config = config;
        this.handler = handler;
        this.bootstrap = makeBootstrap();
    }

    private Bootstrap makeBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup(config.getClientThreads());
        bootstrap.group(group).channel(NioSocketChannel.class); // 使用NioSocketChannel来作为连接用的channel类
        bootstrap.handler(new NettyClientInitializer(stamp, handler));
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeoutMills());
        return bootstrap;
    }

    public void run() {
        connect();
        keepAlive();
    }

    public void connect() {
        LOG.info("Netty client connect, stamp:{}", stamp);
        // 发起异步连接请求，绑定连接端口和host信息
        try {
            final ChannelFuture future = bootstrap.connect(config.getHost(), config.getPort()).sync();
            // channel = future.channel();
            future.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture arg0) throws Exception {
                    if (arg0.isSuccess()) {
                        LOG.info("Netty client connect success, stamp:{}", stamp);
                    } else {
                        LOG.error("Netty client connect failed, stamp:{}, cause", stamp, future.cause());
                        // group.shutdownGracefully(); // 关闭线程组
                    }
                }
            });
        } catch (Exception e) {
            LOG.error("Netty client connect error, stamp:{}, error", stamp, e);
            handler.onError(stamp, e);
        }
    }

    private void keepAlive() {
        NettyTaskScheduler.schedule(new NettyTask() {

            @Override
            public void runInSafe() {
                try {
                    ping();
                } catch (Exception e) {
                    LOG.error("ping error", e);
                    connect();
                }
            }
        }, config.getPingDelayMills(), config.getPingIntervalMillis());
    }

    public String getStamp() {
        return stamp;
    }

    public boolean isConnected() {
        return StampChannelHolder.has(stamp);
    }

    private void ping() {
        NettyMessageSender.ping(stamp);
    }

    public static final Builder builder(String host, int port) {
        return new Builder(host, port);
    }

    public static final class Builder {
        private NettyClientConfig config;
        private NettyClientHandler handler;

        private Builder(String host, int port) {
            this.config = NettyClientConfig.of(host, port);
            this.handler = new DefaultNettyClientHandler();
        }

        public Builder handler(NettyClientHandler handler) {
            Objects.requireNonNull(handler, "handler");
            this.handler = handler;
            return this;
        }

        public NettyClient build() {
            return new NettyClient(config, handler);
        }
    }
}
