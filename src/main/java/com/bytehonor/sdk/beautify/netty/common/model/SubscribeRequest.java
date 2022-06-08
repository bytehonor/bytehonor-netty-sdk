package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeRequest implements Serializable {

    private static final long serialVersionUID = -4282705113537223742L;

    private String subjects;

    private Boolean subscribed;

    public static SubscribeRequest of(String subjects) {
        return of(subjects, true);
    }

    public static SubscribeRequest of(String subjects, boolean subscribed) {
        Objects.requireNonNull(subjects, "subjects");
        SubscribeRequest request = new SubscribeRequest();
        request.setSubjects(subjects);
        request.setSubscribed(subscribed);
        return request;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
