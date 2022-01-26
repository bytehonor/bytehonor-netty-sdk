package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.bytehonor.sdk.netty.bytehonor.common.util.NettyJsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 20220124
 * 
 * @author lijianqiang
 *
 */
public class NettyPayload implements Serializable {

    private static final long serialVersionUID = 2699241336868045496L;

    /**
     * 来自whois
     */
    private String whois;

    /**
     * JsonObject的Clazz全名, 就是subject
     */
    private String subject;

    private String json;

    public static NettyPayload fromJson(String json) {
        Objects.requireNonNull(json, "json");
        return NettyJsonUtils.fromJson(json, NettyPayload.class);
    }

    public static <T> NettyPayload fromOne(T obj) {
        Objects.requireNonNull(obj, "obj");

        ArrayList<T> list = new ArrayList<T>();
        list.add(obj);
        return fromList(list);
    }

    public static <T> NettyPayload fromList(List<T> list) {
        Objects.requireNonNull(list, "list");
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("list cannot be empty");
        }

        NettyPayload model = new NettyPayload();
        model.setSubject(list.get(0).getClass().getName());
        model.setJson(NettyJsonUtils.toJson(list));
        return model;
    }

    public <T> List<T> list(TypeReference<List<T>> valueTypeRef) {
        Objects.requireNonNull(valueTypeRef, "valueTypeRef");
        try {
            return NettyJsonUtils.fromJson(this.json, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> list(Class<T> valueType) {
        Objects.requireNonNull(valueType, "valueType");
        if (valueType.getName().equals(this.subject) == false) {
            throw new RuntimeException(this.subject);
        }
        try {
            return NettyJsonUtils.fromListJson(this.json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T one(Class<T> valueType) {
        Objects.requireNonNull(valueType, "valueType");
        List<T> list = this.list(valueType);
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException(this.subject);
        }
        return list.get(0);
    }

    public String getWhois() {
        return whois;
    }

    public void setWhois(String whois) {
        this.whois = whois;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return NettyJsonUtils.toJson(this);
    }

}
