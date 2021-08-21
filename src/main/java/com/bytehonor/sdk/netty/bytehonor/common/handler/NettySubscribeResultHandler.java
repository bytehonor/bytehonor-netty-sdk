package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeResult;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettySubscribeResultHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeResultHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.SUBSCRIBE_RESULT.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channel:{}", message, channel.id().asLongText());
        }

        NettyPayload payload = NettyPayload.fromJson(message);
        SubscribeResult result = payload.one(SubscribeResult.class);
        LOG.info("completed:{}, name:{}", result.getCompleted(), result.getName());
    }

}
