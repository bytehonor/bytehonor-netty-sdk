package com.bytehonor.sdk.beautify.netty.common.model;

import java.io.Serializable;

public class NettyMission<T> implements Serializable {

    private static final long serialVersionUID = 8710095103736458506L;

    private String stamp;

    private T target;

    public String getStamp() {
        return stamp;
    }

    public static <R> NettyMission<R> of(String stamp, R target) {
        NettyMission<R> result = new NettyMission<R>();
        result.setStamp(stamp);
        result.setTarget(target);
        return result;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

}
