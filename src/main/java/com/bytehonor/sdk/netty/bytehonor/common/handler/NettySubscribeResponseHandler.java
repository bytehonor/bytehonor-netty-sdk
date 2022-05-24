package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyPayload;
import com.bytehonor.sdk.netty.bytehonor.common.model.SubscribeResponse;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class NettySubscribeResponseHandler implements NettyHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeResponseHandler.class);

    @Override
    public int type() {
        return NettyTypeEnum.SUBSCRIBE_RESPONSE.getType();
    }

    @Override
    public void handle(Channel channel, String message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("message:{}, channel:{}", message, channel.id().asLongText());
        }

        NettyPayload payload = NettyPayload.fromJson(message);
        SubscribeResponse response = payload.reflect(SubscribeResponse.class);
        LOG.info("completed:{}, subjects:{}", response.getCompleted(), response.getSubjects());
    }

}
