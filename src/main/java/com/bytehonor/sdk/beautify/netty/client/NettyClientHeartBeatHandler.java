package com.bytehonor.sdk.beautify.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于检测channel的心跳的handler
 *
 * @author lijianqiang
 *
 */
public class NettyClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientHeartBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;// 强制类型转换
            if (LOG.isDebugEnabled()) {
                LOG.debug("state:{}", event.state().name());
            }
            if (event.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                LOG.info("idle server channel:{}", channel.id().asLongText());
            }
        }
    }

}
