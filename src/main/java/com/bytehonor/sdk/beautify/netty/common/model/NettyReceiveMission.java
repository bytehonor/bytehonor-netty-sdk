package com.bytehonor.sdk.beautify.netty.common.model;

public class NettyReceiveMission {

    private String stamp;

    private String text;

    public static NettyReceiveMission of(String stamp, String text) {
        NettyReceiveMission model = new NettyReceiveMission();
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
