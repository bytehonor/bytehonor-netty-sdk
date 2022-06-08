package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeResponse implements Serializable {

    private static final long serialVersionUID = 7625182470937923318L;

    private String subjects;

    private Integer completed;

    public static SubscribeResponse of(String subjects, Integer completed) {
        Objects.requireNonNull(subjects, "subjects");
        SubscribeResponse result = new SubscribeResponse();
        result.setSubjects(subjects);
        result.setCompleted(completed);
        return result;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}
