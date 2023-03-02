package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;

/**
 * 
 * @author lijianqiang
 *
 */
public class NettyFrame implements Serializable {

    private static final long serialVersionUID = 2699241336868045496L;

    public static final String PING = "ping";

    public static final String PONG = "pong";

    public static final String PAYLOAD = "payload";

    public static final String SUBSCRIBE = "subscribe";

    /**
     * ping, pong, payload, subscribe
     */
    private String method;

    private String subject;

    private String body;

    public static NettyFrame fromJson(String json) {
        Objects.requireNonNull(json, "json");
        return NettyJsonUtils.fromJson(json, NettyFrame.class);
    }

    public static NettyFrame ping() {
        NettyFrame model = new NettyFrame();
        model.setMethod(PING);
        model.setSubject(Long.class.getName());
        model.setBody(String.valueOf(System.currentTimeMillis()));
        return model;
    }

    public static NettyFrame pong() {
        NettyFrame model = new NettyFrame();
        model.setMethod(PONG);
        model.setSubject(Long.class.getName());
        model.setBody(String.valueOf(System.currentTimeMillis()));
        return model;
    }

    public static NettyFrame payload(NettyPayload payload) {
        Objects.requireNonNull(payload, "payload");

        NettyFrame model = new NettyFrame();
        model.setMethod(PAYLOAD);
        model.setSubject(payload.getSubject());
        model.setBody(payload.getBody());
        return model;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return NettyJsonUtils.toJson(this);
    }

}
