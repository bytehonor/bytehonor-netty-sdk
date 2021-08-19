package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijianqiang
 *
 */
public class NettyHandlerFactory {

    private static final Map<Integer, NettyHandler> MAP = new HashMap<Integer, NettyHandler>();
    
    static {
        put(new NettyPingHandler());
        put(new NettyPongHandler());
        put(new NettySubscribeHandler());
        put(new NettyUnsubscribeHandler());
    }

    public static void put(NettyHandler handler) {
        if (handler == null) {
            return;
        }
        MAP.put(handler.type(), handler);
    }

    public static NettyHandler get(int type) {
        return MAP.get(type);
    }
}
