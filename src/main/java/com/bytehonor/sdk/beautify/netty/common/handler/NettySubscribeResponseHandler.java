package com.bytehonor.sdk.beautify.netty.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyTypeEnum;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;
import com.bytehonor.sdk.beautify.netty.common.model.SubscribeResponse;

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

//        SubscribeResponse response = NettyPayload.reflect(message, SubscribeResponse.class);
//        LOG.info("completed:{}, subject:{}", response.getCompleted(), response.getSubject());
    }

}
