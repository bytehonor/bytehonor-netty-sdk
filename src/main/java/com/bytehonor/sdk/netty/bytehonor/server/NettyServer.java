package com.bytehonor.sdk.netty.bytehonor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    /**
     * 负责连接请求
     */
    private EventLoopGroup bossGroup;

    /**
     * 负责事件响应
     */
    private EventLoopGroup workerGroup;

    private ServerBootstrap bootstrap;

    private ChannelFuture channelFuture;

    private boolean init = false;

    private NettyServer() {
    }

    /**
     * 延迟加载(线程安全)
     *
     */
    private static class LazyHolder {
        private static NettyServer INSTANCE = new NettyServer();
    }

    private static NettyServer getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void start(int port, boolean ssl) {
        getInstance().bind(port, ssl);
    }

    public void bind(int port, boolean ssl) {
        if (init) {
            return;
        }
        LOG.info("Netty server start, port:{}, ssl:{}", port, ssl);
        init = true;

        // 负责连接请求
        bossGroup = new NioEventLoopGroup(4);
        // 负责事件响应
        workerGroup = new NioEventLoopGroup(8);
        // 负责连接请求
        // EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        // 负责事件响应
        // EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            // 服务器启动项
            bootstrap = new ServerBootstrap();
            // handler是针对bossGroup，childHandler是针对workerHandler
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.option(ChannelOption.SO_BACKLOG, NettyConstants.SO_BACKLOG); // 设置TCP缓冲区
            bootstrap.option(ChannelOption.SO_RCVBUF, NettyConstants.SO_RCVBUF); // 接收客户端信息的最大长度
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 选择nioChannel
            bootstrap.channel(NioServerSocketChannel.class);
            // 日志处理 info级别
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            // 添加自定义的初始化器
            if (ssl) {
                bootstrap.childHandler(new NettyServerSslInitializer());
            } else {
                bootstrap.childHandler(new NettyServerInitializer());
            }

            // 端口绑定
            channelFuture = bootstrap.bind(port);
            LOG.info("Netty Tcp start isSuccess:{}", channelFuture.isSuccess());
            // channelFuture = bootstrap.bind(port).sync();
            // 该方法进行阻塞,等待服务端链路关闭之后继续执行。
            // 这种模式一般都是使用Netty模块主动向服务端发送请求，然后最后结束才使用
            // channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            LOG.error("Netty server start error", e);
        }
    }
}
