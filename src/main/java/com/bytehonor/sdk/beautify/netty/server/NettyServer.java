package com.bytehonor.sdk.beautify.netty.server;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;
import com.bytehonor.sdk.beautify.netty.common.model.NettyServerConfig;
import com.bytehonor.sdk.beautify.netty.common.task.NettyTask;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lijianqiang
 *
 */
public final class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    private final int port;

    private final NettyServerHandler handler;

    private final ServerBootstrap bootstrap;

    private final Thread thread;

    public NettyServer() {
        this(new NettyServerConfig(), new DefaultNettyServerHandler());
    }

    public NettyServer(NettyServerHandler handler) {
        this(new NettyServerConfig(), handler);
    }

    public NettyServer(int port, NettyServerHandler handler) {
        this(NettyServerConfig.of(port), handler);
    }

    public NettyServer(NettyServerConfig config, NettyServerHandler handler) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(handler, "handler");

        this.port = config.getPort();
        this.handler = handler;
        this.bootstrap = makeBootstrap(config);
        this.thread = makeThread();
    }

    private ServerBootstrap makeBootstrap(NettyServerConfig config) {
        // 服务器启动项
        ServerBootstrap bootstrap = new ServerBootstrap();
        // handler是针对bossGroup，childHandler是针对workerHandler
        // 负责连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(config.getBossThreads());
        // 负责事件响应
        EventLoopGroup workerGroup = new NioEventLoopGroup(config.getWorkThreads());
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.option(ChannelOption.SO_BACKLOG, NettyConstants.SO_BACKLOG); // 设置TCP缓冲区
        bootstrap.option(ChannelOption.SO_RCVBUF, NettyConstants.SO_RCVBUF); // 接收客户端信息的最大长度
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 选择nioChannel
        bootstrap.channel(NioServerSocketChannel.class);
        // 日志处理 info级别
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // 添加自定义的初始化器
        bootstrap.childHandler(new NettyServerInitializer(handler));

        return bootstrap;
    }

    private Thread makeThread() {
        Thread thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                doStart();
            }
        });
        thread.setName("netty-server-" + port);
        return thread;
    }

    public void start() {
        thread.start();
    }

    private void doStart() {
        LOG.info("Netty server start, port:{}", port);

        try {
            // 端口绑定
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            LOG.info("Netty Server success:{}, done:{}", channelFuture.isSuccess(), channelFuture.isDone());
            // channelFuture = bootstrap.bind(port).sync();
            // 该方法进行阻塞,等待服务端链路关闭之后继续执行。
            // 这种模式一般都是使用Netty模块主动向服务端发送请求，然后最后结束才使用
            // channelFuture.channel().closeFuture().sync();

            handler.onStarted();
        } catch (Exception e) {
            LOG.error("Netty server start error", e);
            handler.onFailed(e);
        }
    }
}
