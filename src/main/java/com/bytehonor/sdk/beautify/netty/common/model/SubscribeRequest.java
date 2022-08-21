package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 单个主题定
 * 
 * @author lijianqiang
 *
 */
public class SubscribeRequest implements Serializable {

    private static final long serialVersionUID = -4282705113537223742L;

    private String subject;

    private Boolean subscribed;

    public static SubscribeRequest yes(String subject) {
        return of(subject, true);
    }
    
    public static SubscribeRequest no(String subject) {
        return of(subject, false);
    }

    private static SubscribeRequest of(String subject, boolean subscribed) {
        Objects.requireNonNull(subject, "subject");
        SubscribeRequest request = new SubscribeRequest();
        request.setSubject(subject);
        request.setSubscribed(subscribed);
        return request;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
