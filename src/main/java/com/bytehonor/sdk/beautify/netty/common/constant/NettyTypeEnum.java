package com.bytehonor.sdk.beautify.netty.common.constant;

/**
 * <pre>
 * Code    说明
 * 0       未定义
 * 1       ping 请求
 * 2       pong 响应
 * 3       whois 客户端
 * 4       whois 服务端
 * 20      普通传输
 * 
 * </pre>
 * 
 * @author lijianqiang
 *
 */
@Deprecated
public enum NettyTypeEnum {

    UNDEFINED(0, "未定义"),

    PING(1, "ping"),

    PONG(2, "pong"),

    WHOIS_CLIENT(3, "whois_client"),

    WHOIS_SERVER(4, "whois_server"),

    SUBSCRIBE_REQUEST(5, "subscribe_request"),

    SUBSCRIBE_RESPONSE(6, "subscribe_response"),

    PUBLIC_PAYLOAD(20, "普通传输"),

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
