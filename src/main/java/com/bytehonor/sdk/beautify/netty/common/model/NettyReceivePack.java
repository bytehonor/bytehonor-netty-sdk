package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyReceivePack implements Serializable {

    private static final long serialVersionUID = 8180230166940765694L;

    private String stamp;

    private String text;

    public static NettyReceivePack of(String stamp, String text) {
        NettyReceivePack model = new NettyReceivePack();
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
