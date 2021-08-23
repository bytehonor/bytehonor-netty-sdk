package com.bytehonor.sdk.netty.bytehonor.common.constant;

/**
 * <pre>
 * Code    说明
 * 0       为定义
 * 1       ping 请求
 * 2       pong 响应
 * 10      普通传输
 * 
 * </pre>
 * 
 * @author lijianqiang
 *
 */
public enum NettyTypeEnum {

    UNDEFINED(0, "未定义"),

    PING(1, "ping"),

    PONG(2, "pong"),

    SUBSCRIBE_REQUEST(3, "subscribe_request"),

    SUBSCRIBE_RESULT(4, "subscribe_result"),

    APPID(6, "appid"),

    PUBLIC_PAYLOAD(10, "普通传输"),

    ;

    private Integer type;

    private String name;

    private NettyTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static NettyTypeEnum typeOf(Integer type) {
        for (NettyTypeEnum sc : NettyTypeEnum.values()) {
            if (sc.getType().equals(type)) {
                return sc;
            }
        }
        return UNDEFINED;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
