package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

/**
 * @author lijianqiang
 *
 */
public class NettyConfig implements Serializable {

    private static final long serialVersionUID = 1158507238071086040L;

    private String host;

    private int port;

    private String whoiam;

    private boolean ssl;

    private boolean sslEngine;

    private String sslPassword;

    // idle
    private int readIdleSeconds;
    private int writIdleSeconds;
    private int allIdleSeconds;

    // length
    private int maxFrameLength;
    private int lengthFieldOffset;
    private int lengthFieldLength;

    // Threads
    private int bossThreads;
    private int workThreads;
    private int clientThreads;

    private int connectTimeoutMills;

    /**
     * 服务端检查任务周期
     */
    private long periodSeconds;

    public NettyConfig() {
        this.host = "127.0.0.1";
        this.port = 85;
        this.whoiam = "unkwon";
        this.ssl = false;
        this.sslEngine = false;
        this.sslPassword = NettyConstants.SSL_PASSWORD;
        this.readIdleSeconds = NettyConstants.READ_IDLE_TIMEOUT_SECONDS;
        this.writIdleSeconds = NettyConstants.WRITE_IDLE_TIMEOUT_SECONDS;
        this.allIdleSeconds = NettyConstants.ALL_IDLE_TIMEOUT_SECONDS;
        this.maxFrameLength = NettyConstants.MAX_LENGTH;
        this.lengthFieldOffset = NettyConstants.LENGTH_OFFSET;
        this.lengthFieldLength = NettyConstants.LENGTH_SIZE;
        this.bossThreads = NettyConstants.BOSS_THREADS;
        this.workThreads = NettyConstants.WORD_THREADS;
        this.clientThreads = NettyConstants.CLIENT_THREADS;
        this.connectTimeoutMills = NettyConstants.CONNECT_TIMEOUT_MILLIS;
        this.periodSeconds = 100L;
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

    public String getWhoiam() {
        return whoiam;
    }

    public void setWhoiam(String whoiam) {
        this.whoiam = whoiam;
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

    public int getReadIdleSeconds() {
        return readIdleSeconds;
    }

    public void setReadIdleSeconds(int readIdleSeconds) {
        this.readIdleSeconds = readIdleSeconds;
    }

    public int getWritIdleSeconds() {
        return writIdleSeconds;
    }

    public void setWritIdleSeconds(int writIdleSeconds) {
        this.writIdleSeconds = writIdleSeconds;
    }

    public int getAllIdleSeconds() {
        return allIdleSeconds;
    }

    public void setAllIdleSeconds(int allIdleSeconds) {
        this.allIdleSeconds = allIdleSeconds;
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

    public long getPeriodSeconds() {
        return periodSeconds;
    }

    public void setPeriodSeconds(long periodSeconds) {
        this.periodSeconds = periodSeconds;
    }

}
