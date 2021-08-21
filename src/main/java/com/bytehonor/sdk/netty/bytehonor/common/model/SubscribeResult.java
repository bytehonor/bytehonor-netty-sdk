package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeResult implements Serializable {

    private static final long serialVersionUID = 7625182470937923318L;

    private String name;

    private Boolean completed;

    public static SubscribeResult of(String name, boolean completed) {
        Objects.requireNonNull(name, "name");
        SubscribeResult result = new SubscribeResult();
        result.setName(name);
        result.setCompleted(completed);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
