package com.bytehonor.sdk.beautify.netty.common.handler;

import com.bytehonor.sdk.beautify.netty.common.consumer.NettyConsumerFactory;
import com.bytehonor.sdk.beautify.netty.common.model.NettyFrame;
import com.bytehonor.sdk.beautify.netty.common.model.NettyPayload;

/**
 * @author lijianqiang
 *
 */
public class NettySubscribeRequestHandler implements NettyFrameHandler {

//    private static final Logger LOG = LoggerFactory.getLogger(NettySubscribeRequestHandler.class);

    @Override
    public String method() {
        return NettyFrame.SUBSCRIBE;
    }

    @Override
    public void handle(String stamp, NettyPayload payload, NettyConsumerFactory factory) {
//        final ChannelId id = channel.id();
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("message:{}, channel:{}", message, id.asLongText());
//        }
//
//        SubscribeRequest request = null;//NettyPayload.reflect(message, SubscribeRequest.class);
//        if (request.getSubject() == null) {
//            LOG.warn("subscribe subject null");
//            return;
//        }
//
//        LOG.info("subscribe:{}, subject:{}", request.getSubscribed(), request.getSubject());
//        if (request.getSubscribed()) {
//            SubjectChannelCacheHolder.put(request.getSubject(), id);
//        } else {
//            SubjectChannelCacheHolder.remove(request.getSubject(), id);
//        }
//
//        SubscribeResponse result = SubscribeResponse.of(request.getSubject(), 1);
//        NettyMessageSender.subscribeResponse(channel, result);
    }

}
