package com.bytehonor.sdk.netty.bytehonor.common.constant;

/**
 * <pre>
 * Code    说明
 * 0x01    下发操作指令
 * 0x02    操作指令回应
 * 0x03    设备主动上报
 * 0xff    设备异常（硬件）
 * 
 * </pre>
 * 
 * @author lijianqiang
 *
 */
public enum CommandEnum {

    UNDEFINED("UNDEFINED", "未定义"),
    
    OX01("01", "下发操作指令"),
    OX02("02", "操作指令回应"),
    OX03("03", "设备主动上报"),
    OXFF("ff", "设备异常（硬件）"),

    ;

    private String key;

    private String name;

    private CommandEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static CommandEnum keyOf(String key) {
        for (CommandEnum sc : CommandEnum.values()) {
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
