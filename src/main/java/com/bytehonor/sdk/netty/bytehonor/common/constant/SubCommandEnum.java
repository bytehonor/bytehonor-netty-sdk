package com.bytehonor.sdk.netty.bytehonor.common.constant;

/**
 * <pre>
 * Code 说明
 * 0x0000  心跳包
 * 0x0001  关机命令
 * 0x0002  开机命令
 * 0x0003  强冲命令
 * 0x0004  ID 编码上报
 * 0x0005  系统初始化
 * 0x0006  用水同步命令
 * 0x0007  滤芯复位
 * 0x0008  时钟同步
 * 0x0009  日志查询
 * 0x000A  恢复出厂设置（解绑）
 * 0x000B  用时同步
 * 0x000C  设备状态变更
 * 0x000D  查询设备信息
 * 0x000E  获取信号和ICCID
 * 0x000F  机器锁定命令
 * 0x0010  取水启动命令
 * 0x0011  取水解锁命令
 * 0x0012  设备自检命令
 * 0x0013  ROM升级命令
 * 0x0014  ROM升级数据包
 * 0x0015  查询基站定位
 * 0x0016  查询设备ID
 * 0x0017  故障保护设定
 * 0x0018  故障保护查询
 * 0x0019  单次流量查询
 * 
 * </pre>
 * 
 * @author lijianqiang
 *
 */
public enum SubCommandEnum {

    UNDEFINED("UNDEFINED", "未定义"),
    
    OX0000("0000", "心跳包"),
    OX0001("0001", "关机命令"),
    OX0002("0002", "开机命令"),
    OX0003("0003", "强冲命令"),
    OX0004("0004", "ID 编码上报"),
    OX0005("0005", "系统初始化"),
    OX0006("0006", "用水同步命令"),
    OX0007("0007", "滤芯复位"),
    OX0008("0008", "时钟同步"),
    OX0009("0009", "日志查询"),
    OX000A("000a", "恢复出厂设置（解绑）"),
    OX000B("000b", "用时同步"),
    OX000C("000c", "设备状态变更"),
    OX000D("000d", "查询设备信息"),
    OX000E("000e", "获取信号和ICCID"),
    OX000F("000f", "机器锁定命令"),
    OX0010("0010", "取水启动命令"),
    OX0011("0011", "取水解锁命令"),
    OX0012("0012", "设备自检命令"),
    OX0013("0013", "ROM升级命令"),
    OX0014("0014", "ROM升级数据包"),
    OX0015("0015", "查询基站定位"),
    OX0016("0016", "查询设备ID"),
    OX0017("0017", "故障保护设定"),
    OX0018("0018", "故障保护查询"),
    OX0019("0019", "单次流量查询"),

    ;

    private String key;

    private String name;

    private SubCommandEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static SubCommandEnum keyOf(String key) {
        for (SubCommandEnum sc : SubCommandEnum.values()) {
            if (sc.getKey().equals(key)) {
                return sc;
            }
        }
        return UNDEFINED;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
