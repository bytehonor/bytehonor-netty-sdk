package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeRequest implements Serializable {

    private static final long serialVersionUID = -4282705113537223742L;

    private String name;

    private Boolean subscribed;

    public static SubscribeRequest of(String name) {
        return of(name, true);
    }

    public static SubscribeRequest of(String name, boolean subscribed) {
        Objects.requireNonNull(name, "name");
        SubscribeRequest request = new SubscribeRequest();
        request.setName(name);
        request.setSubscribed(subscribed);
        return request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
