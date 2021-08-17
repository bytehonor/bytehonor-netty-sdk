package com.bytehonor.sdk.netty.bytehonor.client;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.handler.NettyMessageSender;

import io.netty.channel.Channel;

public class NettyClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientTest.class);

    @Test
    public void test() {
        boolean isOk = true;
        NettyClient client = new NettyClient("twitter.bytehonor.com", 81);
        try {
            client.start();
            final Channel channel = client.getChannel();
            NettyMessageSender.send(channel, "hello world");

            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        NettyMessageSender.ping(channel);
                    } catch (Exception e) {
                        LOG.error("ping error:{}", e.getMessage());
                    }
                }
            };
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, 10, 50, TimeUnit.SECONDS);

            Thread.sleep(125000L);
        } catch (InterruptedException e) {
            LOG.error("error", e);
            isOk = false;
        }

        assertTrue("test", isOk);
    }

}
