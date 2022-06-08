package com.bytehonor.sdk.beautify.netty.common.limiter;

import java.util.Objects;

public class SubjectLimiter {

    private String subject;

    private int limit;

    public static SubjectLimiter of(String subject, int limit) {
        Objects.requireNonNull(subject, "subject");
        SubjectLimiter model = new SubjectLimiter();
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
