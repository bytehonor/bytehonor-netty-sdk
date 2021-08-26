package com.bytehonor.sdk.netty.bytehonor.common.util;

import org.junit.Test;

public class NettyEnvUtilsTest {

    @Test
    public void test() {
        String ip = NettyEnvUtils.localIp();
        System.out.println(ip);
    }

}
