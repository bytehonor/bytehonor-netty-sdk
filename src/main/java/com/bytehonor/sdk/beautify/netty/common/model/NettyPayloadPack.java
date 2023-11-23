package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyPayloadPack implements Serializable {

    private static final long serialVersionUID = 172171646624412675L;

    private String stamp;

    private NettyPayload payload;

    public static NettyPayloadPack of(String stamp, NettyPayload payload) {
        NettyPayloadPack model = new NettyPayloadPack();
        model.setStamp(stamp);
        model.setPayload(payload);
        return model;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public NettyPayload getPayload() {
        return payload;
    }

    public void setPayload(NettyPayload payload) {
        this.payload = payload;
    }

}
