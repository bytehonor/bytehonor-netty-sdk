package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.util.Objects;

import org.springframework.core.env.Environment;

public class NettyEnvUtils {

    public static String makeId(Environment env) {
        Objects.requireNonNull(env, "env");
        return new StringBuilder().append(env.getProperty("spring.application.name")).append(":")
                .append(env.getProperty("server.port")).toString();
    }
}
