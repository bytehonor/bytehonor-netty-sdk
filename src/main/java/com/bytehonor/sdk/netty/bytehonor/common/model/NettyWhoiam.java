package com.bytehonor.sdk.netty.bytehonor.common.model;

public class NettyWhoiam {

    private String iam;

    public NettyWhoiam() {
        this("unknown");
    }

    public NettyWhoiam(String iam) {
        this.iam = iam;
    }

    public String getIam() {
        return iam;
    }

    public void setIam(String iam) {
        this.iam = iam;
    }

}
