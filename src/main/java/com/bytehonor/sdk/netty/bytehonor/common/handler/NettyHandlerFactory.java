package com.bytehonor.sdk.netty.bytehonor.common.handler;

import java.util.HashMap;
import java.util.Map;

public class NettyHandlerFactory {

    private static final Map<String, NettyHandler> MAP = new HashMap<String, NettyHandler>();
    
    public static void put(NettyHandler handler) {
        if (handler == null) {
            return;
        }
        MAP.put(handler.matchCmd(), handler);
    }

    public static NettyHandler get(String cmd) {
        return MAP.get(cmd);
    }
}
