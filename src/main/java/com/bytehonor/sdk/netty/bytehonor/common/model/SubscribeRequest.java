package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeRequest implements Serializable {

    private static final long serialVersionUID = -4282705113537223742L;

    private String category;

    private Boolean subscribed;

    public static SubscribeRequest of(String category) {
        return of(category, true);
    }

    public static SubscribeRequest of(String category, boolean subscribed) {
        Objects.requireNonNull(category, "category");
        SubscribeRequest request = new SubscribeRequest();
        request.setCategory(category);
        request.setSubscribed(subscribed);
        return request;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
