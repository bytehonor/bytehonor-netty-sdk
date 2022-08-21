package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lijianqiang
 *
 */
public class SubscribeResponse implements Serializable {

    private static final long serialVersionUID = 7625182470937923318L;

    private String subject;

    private Integer completed;

    public static SubscribeResponse of(String subject, Integer completed) {
        Objects.requireNonNull(subject, "subject");
        SubscribeResponse result = new SubscribeResponse();
        result.setSubject(subject);
        result.setCompleted(completed);
        return result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}
