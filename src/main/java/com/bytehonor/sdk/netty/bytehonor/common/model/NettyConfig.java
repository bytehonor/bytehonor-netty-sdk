package com.bytehonor.sdk.netty.bytehonor.common.model;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;

public class NettyConfig {

    private String host;

    private int port;

    private boolean ssl;

    private boolean sslEngine;

    private String sslPassword;

    // idle
    private int readIdleTimeSeconds;
    private int writIdleTimeSeconds;
    private int allIdleTimeSeconds;

    // length
    private int maxFrameLength;
    private int lengthFieldOffset;
    private int lengthFieldLength;

    // Threads
    private int bossThreads;
    private int workThreads;
    private int clientThreads;

    private int connectTimeoutMills;

    public NettyConfig() {
        this.host = "127.0.0.1";
        this.port = 81;
        this.ssl = false;
        this.sslEngine = false;
        this.sslPassword = NettyConstants.SSL_PASSWORD;
        this.readIdleTimeSeconds = NettyConstants.READ_IDLE_TIMEOUT_SECONDS;
        this.writIdleTimeSeconds = NettyConstants.WRITE_IDLE_TIMEOUT_SECONDS;
        this.allIdleTimeSeconds = NettyConstants.ALL_IDLE_TIMEOUT_SECONDS;
        this.maxFrameLength = NettyConstants.MAX_LENGTH;
        this.lengthFieldOffset = NettyConstants.LENGTH_OFFSET;
        this.lengthFieldLength = NettyConstants.LENGTH_SIZE;
        this.bossThreads = NettyConstants.BOSS_THREADS;
        this.workThreads = NettyConstants.WORD_THREADS;
        this.clientThreads = NettyConstants.CLIENT_THREADS;
        this.connectTimeoutMills = NettyConstants.CONNECT_TIMEOUT_MILLIS;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isSslEngine() {
        return sslEngine;
    }

    public void setSslEngine(boolean sslEngine) {
        this.sslEngine = sslEngine;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }

    public int getReadIdleTimeSeconds() {
        return readIdleTimeSeconds;
    }

    public void setReadIdleTimeSeconds(int readIdleTimeSeconds) {
        this.readIdleTimeSeconds = readIdleTimeSeconds;
    }

    public int getWritIdleTimeSeconds() {
        return writIdleTimeSeconds;
    }

    public void setWritIdleTimeSeconds(int writIdleTimeSeconds) {
        this.writIdleTimeSeconds = writIdleTimeSeconds;
    }

    public int getAllIdleTimeSeconds() {
        return allIdleTimeSeconds;
    }

    public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
        this.allIdleTimeSeconds = allIdleTimeSeconds;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public int getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public void setLengthFieldOffset(int lengthFieldOffset) {
        this.lengthFieldOffset = lengthFieldOffset;
    }

    public int getLengthFieldLength() {
        return lengthFieldLength;
    }

    public void setLengthFieldLength(int lengthFieldLength) {
        this.lengthFieldLength = lengthFieldLength;
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public int getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(int clientThreads) {
        this.clientThreads = clientThreads;
    }

    public int getConnectTimeoutMills() {
        return connectTimeoutMills;
    }

    public void setConnectTimeoutMills(int connectTimeoutMills) {
        this.connectTimeoutMills = connectTimeoutMills;
    }

}
