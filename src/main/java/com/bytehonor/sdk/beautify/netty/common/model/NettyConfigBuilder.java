package com.bytehonor.sdk.beautify.netty.common.model;

/**
 * @author lijianqiang
 *
 */
@Deprecated
public class NettyConfigBuilder {

    private NettyClientConfig config;

    private NettyConfigBuilder() {
        this.config = new NettyClientConfig();
    }

    public NettyClientConfig build() {
        return config;
    }

    public static NettyConfigBuilder server(int port) {
        NettyConfigBuilder builder = new NettyConfigBuilder();
        builder.config.setPort(port);
        return builder;
    }

    public static NettyConfigBuilder client(String host, int port) {
        NettyConfigBuilder builder = new NettyConfigBuilder();
        builder.config.setHost(host);
        builder.config.setPort(port);
        return builder;
    }

    public NettyConfigBuilder ssl(boolean sslEnabled) {
        return ssl(sslEnabled, false, null);
    }

    public NettyConfigBuilder ssl(boolean sslEnabled, boolean sslEngine, String sslPassword) {
        this.config.setSsl(sslEnabled);
        this.config.setSslEngine(sslEngine);
        this.config.setSslPassword(sslPassword);
        return this;
    }

//    public NettyConfigBuilder lengths(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
//        this.config.setMaxFrameLength(maxFrameLength);
//        this.config.setLengthFieldOffset(lengthFieldOffset);
//        this.config.setLengthFieldLength(lengthFieldLength);
//        return this;
//    }
//
//    public NettyConfigBuilder servers(int bossThreads, int workThreads) {
//        this.config.setBossThreads(bossThreads);
//        this.config.setWorkThreads(workThreads);
//        return this;
//    }

    public NettyConfigBuilder clients(int clientThreads, int connectTimeoutMills) {
        this.config.setClientThreads(clientThreads);
        this.config.setConnectTimeoutMills(connectTimeoutMills);
        return this;
    }

//    public NettyConfigBuilder idles(int readIdleSeconds, int writIdleSeconds, int allIdleSeconds) {
//        this.config.setReadIdleSeconds(readIdleSeconds);
//        this.config.setWritIdleSeconds(writIdleSeconds);
//        this.config.setAllIdleSeconds(allIdleSeconds);
//        return this;
//    }

}
