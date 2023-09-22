package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettySendPack implements Serializable {

    private static final long serialVersionUID = -7687487738922929399L;

    private String stamp;
    private NettyFrame frame;

    public static NettySendPack of(String stamp, NettyFrame frame) {
        NettySendPack model = new NettySendPack();
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

    public NettyFrame getFrame() {
        return frame;
    }

    public void setFrame(NettyFrame frame) {
        this.frame = frame;
    }

}
