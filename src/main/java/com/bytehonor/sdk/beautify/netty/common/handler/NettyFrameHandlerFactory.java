package com.bytehonor.sdk.beautify.netty.common.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijianqiang
 *
 */
public class NettyFrameHandlerFactory {

    private final Map<String, NettyFrameHandler> map;

    public NettyFrameHandlerFactory() {
        map = new HashMap<String, NettyFrameHandler>();
        add(new NettyFramePingHandler());
        add(new NettyFramePongHandler());
        add(new NettyFramePayloadHandler());
    }

    public void add(NettyFrameHandler handler) {
        if (handler == null) {
            return;
        }
        map.put(handler.method(), handler);
    }

    public NettyFrameHandler get(String method) {
        return map.get(method);
    }
}
