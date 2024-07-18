package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyMessage implements Serializable {

    private static final long serialVersionUID = 8180230166940765694L;

    private String stamp;

    /**
     * NettyFrame
     */
    private String frame;

    public static NettyMessage of(String stamp, String frame) {
        NettyMessage model = new NettyMessage();
        model.setStamp(stamp);
        model.setFrame(frame);
        return model;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

}
