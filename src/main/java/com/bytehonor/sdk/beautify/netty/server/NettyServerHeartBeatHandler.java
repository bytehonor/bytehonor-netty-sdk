package com.bytehonor.sdk.beautify.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.cache.ChannelCacheHolder;
import com.bytehonor.sdk.beautify.netty.common.cache.StampChannelHolder;
import com.bytehonor.sdk.beautify.netty.common.util.NettyChannelUtils;

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
public class NettyServerHeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerHeartBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;// 强制类型转换
            if (LOG.isDebugEnabled()) {
                LOG.debug("state:{}", event.state().name());
            }
            if (event.state() == IdleState.ALL_IDLE) {
                LOG.info("before close channel size:{}", ChannelCacheHolder.size());
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                String stamp = NettyChannelUtils.stamp(channel);
                LOG.info("idle client stamp:{}", stamp);
                ChannelCacheHolder.remove(channel);
                StampChannelHolder.remove(stamp);
                channel.close();
                LOG.info("after close channel size:{}", ChannelCacheHolder.size());
            }
        }
    }

}
