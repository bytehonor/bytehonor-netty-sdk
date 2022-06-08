package com.bytehonor.sdk.beautify.netty.common.util;

import java.util.Objects;

public class ClasszUtils {

    public static Class<?> find(String className) throws ClassNotFoundException {
        Objects.requireNonNull(className, "className");
        return Class.forName(className);
    }
}
