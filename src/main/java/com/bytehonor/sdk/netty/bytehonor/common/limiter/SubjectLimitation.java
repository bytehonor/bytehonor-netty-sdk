package com.bytehonor.sdk.netty.bytehonor.common.limiter;

import java.util.Objects;

public class SubjectLimitation {

    private String subject;

    private int limit;

    public static SubjectLimitation of(String subject, int limit) {
        Objects.requireNonNull(subject, "subject");
        SubjectLimitation model = new SubjectLimitation();
        model.setSubject(subject);
        model.setLimit(limit);
        return model;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
