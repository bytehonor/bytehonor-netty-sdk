package com.bytehonor.sdk.netty.bytehonor.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.constant.CommandEnum;
import com.bytehonor.sdk.netty.bytehonor.common.constant.SubCommandEnum;
import com.bytehonor.sdk.netty.bytehonor.common.model.NettyContent;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyDataUtils;

import io.netty.channel.Channel;

public class NettyMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageReceiver.class);

    public static void receive(Channel channel, String msg) {
        // 检查协议是否头尾完整
        NettyDataUtils.checkProtocol(msg);
        NettyContent content = NettyDataUtils.parseFromServer(msg);

        String cmd = content.getCmd();
        String subCmd = content.getSubCmd();
        String channelId = channel.id().asLongText();
        LOG.info("receive cmd:{}({}), subCmd:{}({}), data:{} channelId:{}", cmd, CommandEnum.keyOf(cmd).getName(),
                subCmd, SubCommandEnum.keyOf(subCmd).getName(), content.getData(), channelId);

        NettyHandler handler = NettyHandlerFactory.get(cmd);
        if (handler == null) {
            LOG.error("receive cmd:{} no handler! msg:{}, channelId:{}, unknown cmd!!!", cmd, msg, channelId);
            return;
        }
        handler.handle(channel, content);
    }
}
