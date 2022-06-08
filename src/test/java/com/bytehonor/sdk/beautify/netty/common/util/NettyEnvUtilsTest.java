package com.bytehonor.sdk.beautify.netty.common.util;

import org.junit.Test;

public class NettyEnvUtilsTest {

    @Test
    public void test() {
        String ip = NettyEnvUtils.localIp();
        System.out.println(ip);
    }

}
