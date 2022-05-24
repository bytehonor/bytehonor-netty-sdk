package com.bytehonor.sdk.netty.bytehonor.common.model;

public class NettyConfigBuilder {

    private NettyConfig config;

    private NettyConfigBuilder() {
        this.config = new NettyConfig();
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
    
    public NettyConfigBuilder whoiam(String whoiam) {
        this.config.setWhoiam(whoiam);
        return this;
    }

    public NettyConfig build() {
        return config;
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

    public NettyConfigBuilder lengths(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        this.config.setMaxFrameLength(maxFrameLength);
        this.config.setLengthFieldOffset(lengthFieldOffset);
        this.config.setLengthFieldLength(lengthFieldLength);
        return this;
    }

    public NettyConfigBuilder servers(int readIdleTimeSeconds, int writIdleTimeSeconds, int allIdleTimeSeconds,
            int bossThreads, int workThreads) {
        this.config.setReadIdleTimeSeconds(readIdleTimeSeconds);
        this.config.setWritIdleTimeSeconds(writIdleTimeSeconds);
        this.config.setAllIdleTimeSeconds(allIdleTimeSeconds);
        this.config.setBossThreads(bossThreads);
        this.config.setWorkThreads(workThreads);
        return this;
    }

    public NettyConfigBuilder clients(int clientThreads, int connectTimeoutMills) {
        this.config.setClientThreads(clientThreads);
        this.config.setConnectTimeoutMills(connectTimeoutMills);
        return this;
    }

}
