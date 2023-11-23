package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyPayloadMission implements Serializable {

    private static final long serialVersionUID = -5231704884769543147L;

    private String stamp;

    private NettyPayload payload;

    public static NettyPayloadMission of(String stamp, NettyPayload payload) {
        NettyPayloadMission model = new NettyPayloadMission();
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
