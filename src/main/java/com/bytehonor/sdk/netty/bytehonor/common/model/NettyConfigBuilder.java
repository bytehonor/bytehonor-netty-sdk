package com.bytehonor.sdk.netty.bytehonor.common.model;

public class NettyConfigBuilder {

    private NettyConfig config;

    private NettyConfigBuilder() {
        this.config = new NettyConfig();
    }

    public static NettyConfigBuilder make() {
        return new NettyConfigBuilder();
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

    public NettyConfigBuilder idles(int readIdleTimeSeconds, int writIdleTimeSeconds, int allIdleTimeSeconds) {
        this.config.setReadIdleTimeSeconds(readIdleTimeSeconds);
        this.config.setWritIdleTimeSeconds(writIdleTimeSeconds);
        this.config.setAllIdleTimeSeconds(allIdleTimeSeconds);
        return this;
    }

    public NettyConfigBuilder threads(int bossThreads, int workThreads, int clientThreads) {
        this.config.setBossThreads(bossThreads);
        this.config.setWorkThreads(workThreads);
        this.config.setClientThreads(clientThreads);
        return this;
    }
}
