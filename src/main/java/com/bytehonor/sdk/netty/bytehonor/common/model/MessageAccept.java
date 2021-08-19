package com.bytehonor.sdk.netty.bytehonor.common.model;

public class MessageAccept {

    private String value;

    private Boolean status;

    public static MessageAccept of(String value, Boolean status) {
        MessageAccept model = new MessageAccept();
        model.setValue(value);
        model.setStatus(status);
        return model;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
