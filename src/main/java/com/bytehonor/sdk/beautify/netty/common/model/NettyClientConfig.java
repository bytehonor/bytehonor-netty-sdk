package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;
import com.bytehonor.sdk.beautify.netty.common.util.NettyEnvUtils;

/**
 * @author lijianqiang
 *
 */
public class NettyClientConfig implements Serializable {

    private static final long serialVersionUID = 1158507238071086040L;

    private String host;

    private int port;

    private boolean ssl;

    private boolean sslEngine;

    private String sslPassword;

    private int clientThreads;

    private int connectTimeoutMills;

    private long pingDelayMills;

    private long pingIntervalMillis;

    public NettyClientConfig() {
        this.host = "127.0.0.1";
        this.port = 85;
        this.ssl = false;
        this.sslEngine = false;
        this.sslPassword = NettyConstants.SSL_PASSWORD;
        this.clientThreads = NettyEnvUtils.halfThreads();
        this.connectTimeoutMills = NettyConstants.CONNECT_TIMEOUT_MILLIS;
        this.pingDelayMills = 1000L * 15;
        this.pingIntervalMillis = 1000L * 45;
    }

    public static NettyClientConfig of(String host, int port) {
        NettyClientConfig config = new NettyClientConfig();
        config.setHost(host);
        config.setPort(port);
        return config;
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

//    public int getReadIdleSeconds() {
//        return readIdleSeconds;
//    }
//
//    public void setReadIdleSeconds(int readIdleSeconds) {
//        this.readIdleSeconds = readIdleSeconds;
//    }
//
//    public int getWritIdleSeconds() {
//        return writIdleSeconds;
//    }
//
//    public void setWritIdleSeconds(int writIdleSeconds) {
//        this.writIdleSeconds = writIdleSeconds;
//    }
//
//    public int getAllIdleSeconds() {
//        return allIdleSeconds;
//    }
//
//    public void setAllIdleSeconds(int allIdleSeconds) {
//        this.allIdleSeconds = allIdleSeconds;
//    }
//
//    public int getMaxFrameLength() {
//        return maxFrameLength;
//    }
//
//    public void setMaxFrameLength(int maxFrameLength) {
//        this.maxFrameLength = maxFrameLength;
//    }
//
//    public int getLengthFieldOffset() {
//        return lengthFieldOffset;
//    }
//
//    public void setLengthFieldOffset(int lengthFieldOffset) {
//        this.lengthFieldOffset = lengthFieldOffset;
//    }
//
//    public int getLengthFieldLength() {
//        return lengthFieldLength;
//    }
//
//    public void setLengthFieldLength(int lengthFieldLength) {
//        this.lengthFieldLength = lengthFieldLength;
//    }
//
//    public int getBossThreads() {
//        return bossThreads;
//    }
//
//    public void setBossThreads(int bossThreads) {
//        this.bossThreads = bossThreads;
//    }
//
//    public int getWorkThreads() {
//        return workThreads;
//    }
//
//    public void setWorkThreads(int workThreads) {
//        this.workThreads = workThreads;
//    }

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

    public long getPingDelayMills() {
        return pingDelayMills;
    }

    public void setPingDelayMills(long pingDelayMills) {
        this.pingDelayMills = pingDelayMills;
    }

    public long getPingIntervalMillis() {
        return pingIntervalMillis;
    }

    public void setPingIntervalMillis(long pingIntervalMillis) {
        this.pingIntervalMillis = pingIntervalMillis;
    }

}
