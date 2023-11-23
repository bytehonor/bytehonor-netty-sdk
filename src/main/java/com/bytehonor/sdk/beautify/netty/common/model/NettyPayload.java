package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;

/**
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

    private static NettyPayload of(String subject, String body) {
        Objects.requireNonNull(subject, "subject");
        Objects.requireNonNull(body, "body");

        NettyPayload model = new NettyPayload();
        model.setSubject(subject);
        model.setBody(body);
        return model;
    }

    public static <T extends Serializable> NettyPayload of(T obj) {
        Objects.requireNonNull(obj, "obj");

        return of(obj.getClass().getName(), NettyJsonUtils.toJson(obj));
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
