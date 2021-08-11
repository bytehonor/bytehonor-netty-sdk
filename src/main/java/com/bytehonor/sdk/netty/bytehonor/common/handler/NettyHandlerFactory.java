package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.HashMap;
import java.util.Map;

public class NettyHandlerFactory {

    private static final Map<Integer, NettyHandler> MAP = new HashMap<Integer, NettyHandler>();
    
    static {
        put(new NettyHeartHandler());
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
