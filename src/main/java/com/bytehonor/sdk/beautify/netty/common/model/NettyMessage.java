package com.bytehonor.sdk.beautify.netty.common.model;

public class NettyMessage {

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
