package com.bytehonor.sdk.netty.bytehonor.common.model;

public class NettyContent {

    private String cmd;

    private String subCmd;

    private String data;

    private String deviceId;

    public NettyContent(String cmd, String subCmd, String data, String deviceId) {
        this.cmd = cmd;
        this.subCmd = subCmd;
        this.data = data;
        this.deviceId = deviceId;
    }

    public NettyContent() {
        this(null, null, null, null);
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getSubCmd() {
        return subCmd;
    }

    public void setSubCmd(String subCmd) {
        this.subCmd = subCmd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
