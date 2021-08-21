package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeResult implements Serializable {

    private static final long serialVersionUID = 7625182470937923318L;

    private String names;

    private Integer completed;

    public static SubscribeResult of(String names, Integer completed) {
        Objects.requireNonNull(names, "names");
        SubscribeResult result = new SubscribeResult();
        result.setNames(names);
        result.setCompleted(completed);
        return result;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}
