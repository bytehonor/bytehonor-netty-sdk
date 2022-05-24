package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

import com.bytehonor.sdk.netty.bytehonor.common.util.NettyJsonUtils;

/**
 * 20220124
 * 
 * 20220514, body只能是String或json
 * 
 * @author lijianqiang
 *
 */
public class NettyPayload implements Serializable {

    private static final long serialVersionUID = 2699241336868045496L;

    /**
     * Body Object的Clazz全名就是subject
     */
    private String subject;

    private String body;

    public static NettyPayload fromJson(String json) {
        Objects.requireNonNull(json, "json");
        return NettyJsonUtils.fromJson(json, NettyPayload.class);
    }

    public static <T extends Serializable> NettyPayload build(T obj) {
        Objects.requireNonNull(obj, "obj");

        NettyPayload model = new NettyPayload();
        model.setSubject(obj.getClass().getName());
        model.setBody(NettyJsonUtils.toJson(obj));
        return model;
    }

    public static <T> T reflect(String json, Class<T> valueType) {
        NettyPayload payload = fromJson(json);

        return payload.reflect(valueType);
    }

    public <T> T reflect(Class<T> valueType) {
        Objects.requireNonNull(valueType, "valueType");
        if (subject.equals(valueType.getName()) == false) {
            throw new RuntimeException("Class not match: " + subject);
        }
        try {
            return NettyJsonUtils.fromJson(body, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
