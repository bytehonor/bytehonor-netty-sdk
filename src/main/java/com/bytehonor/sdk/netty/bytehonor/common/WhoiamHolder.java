package com.bytehonor.sdk.netty.bytehonor.common;

public class WhoiamHolder {

    private String iam;

    private WhoiamHolder() {
        this.iam = "unknow";
    }

    private static class LazyHolder {
        private static WhoiamHolder INSTANCE = new WhoiamHolder();
    }

    public static String whoiam() {
        return getInstance().getIam();
    }
    
    public static void init(String iam) {
        getInstance().setIam(iam);
    }

    private static WhoiamHolder getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getIam() {
        return iam;
    }

    public void setIam(String iam) {
        this.iam = iam;
    }

}
