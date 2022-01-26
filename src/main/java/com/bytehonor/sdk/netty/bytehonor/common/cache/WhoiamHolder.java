package com.bytehonor.sdk.netty.bytehonor.common.cache;

public class WhoiamHolder {

    private static String whoiam = "unknow";

    public static String getWhoiam() {
        return whoiam;
    }

    public static void setWhoiam(String whoiam) {
        WhoiamHolder.whoiam = whoiam;
    }
}
