package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyMessage implements Serializable {

    private static final long serialVersionUID = 8180230166940765694L;

    private String stamp;

    private String text;

    public static NettyMessage of(String stamp, String text) {
        NettyMessage model = new NettyMessage();
        model.setStamp(stamp);
        model.setText(text);
        return model;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
