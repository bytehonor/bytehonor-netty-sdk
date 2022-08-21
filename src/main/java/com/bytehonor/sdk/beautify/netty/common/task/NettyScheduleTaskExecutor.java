package com.bytehonor.sdk.beautify.netty.common.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lijianqiang
 *
 */
public class NettyScheduleTaskExecutor {

    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static void scheduleAtFixedRate(NettyTask command, long delaySeconds, long periodSeconds) {
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        SERVICE.scheduleAtFixedRate(command, delaySeconds, periodSeconds, TimeUnit.SECONDS);
    }
}
