package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeResult implements Serializable {

    private static final long serialVersionUID = 7625182470937923318L;

    private String category;

    private Boolean completed;

    public static SubscribeResult of(String category, boolean completed) {
        Objects.requireNonNull(category, "category");
        SubscribeResult result = new SubscribeResult();
        result.setCategory(category);
        result.setCompleted(completed);
        return result;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
