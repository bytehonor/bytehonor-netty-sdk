package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeRequest implements Serializable {

    private static final long serialVersionUID = -4282705113537223742L;

    private String names;

    private Boolean subscribed;

    public static SubscribeRequest of(String names) {
        return of(names, true);
    }

    public static SubscribeRequest of(String names, boolean subscribed) {
        Objects.requireNonNull(names, "names");
        SubscribeRequest request = new SubscribeRequest();
        request.setNames(names);
        request.setSubscribed(subscribed);
        return request;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
