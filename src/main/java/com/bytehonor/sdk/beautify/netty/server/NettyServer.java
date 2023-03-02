package com.bytehonor.sdk.beautify.netty.server;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;
import com.bytehonor.sdk.beautify.netty.common.listener.DefaultNettyServerHandler;
import com.bytehonor.sdk.beautify.netty.common.listener.NettyServerHandler;
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
public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    private static final AtomicLong AL = new AtomicLong(0);

    private final NettyServerConfig config;

    private final NettyServerHandler handler;

    private final ServerBootstrap bootstrap;

    private final Thread thread;

    public NettyServer() {
        this(new NettyServerConfig(), new DefaultNettyServerHandler());
    }

    public NettyServer(NettyServerConfig config, NettyServerHandler handler) {
        this.config = config;
        this.handler = handler;
        this.bootstrap = new ServerBootstrap();
        this.thread = thread();
    }

    private Thread thread() {
        Thread thread = new Thread(new NettyTask() {

            @Override
            public void runInSafe() {
                doStart();
            }
        });
        thread.setName(NettyServer.class.getSimpleName() + "-" + AL.incrementAndGet());
        return thread;
    }

    public void start() {
        thread.start();
    }

    private void doStart() {
        LOG.info("Netty server start, port:{}, ssl:{}", config.getPort(), config.isSsl());

        // 负责连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(config.getBossThreads());
        // 负责事件响应
        EventLoopGroup workerGroup = new NioEventLoopGroup(config.getWorkThreads());

        try {
            // 服务器启动项
            // bootstrap = new ServerBootstrap();
            // handler是针对bossGroup，childHandler是针对workerHandler
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.option(ChannelOption.SO_BACKLOG, NettyConstants.SO_BACKLOG); // 设置TCP缓冲区
            bootstrap.option(ChannelOption.SO_RCVBUF, NettyConstants.SO_RCVBUF); // 接收客户端信息的最大长度
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 选择nioChannel
            bootstrap.channel(NioServerSocketChannel.class);
            // 日志处理 info级别
            bootstrap.handler(new LoggingHandler(LogLevel.WARN));
            // 添加自定义的初始化器
            bootstrap.childHandler(new NettyServerInitializer(config, handler));

            // 端口绑定
            ChannelFuture channelFuture = bootstrap.bind(config.getPort()).sync();
            LOG.info("Netty Server isSuccess:{}, idDone:{}", channelFuture.isSuccess(), channelFuture.isDone());
            // channelFuture = bootstrap.bind(port).sync();
            // 该方法进行阻塞,等待服务端链路关闭之后继续执行。
            // 这种模式一般都是使用Netty模块主动向服务端发送请求，然后最后结束才使用
            // channelFuture.channel().closeFuture().sync();

            handler.onSucceed();
        } catch (Exception e) {
            LOG.error("Netty server start error", e);
            handler.onFailed(e);
        }
    }
}
