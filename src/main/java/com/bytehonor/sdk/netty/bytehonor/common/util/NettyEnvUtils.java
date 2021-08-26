package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class NettyEnvUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyEnvUtils.class);

    public static String whoiam(int port) {
        return whoiam(localIp(), port);
    }

    public static String whoiam(String localIp, int port) {
        return new StringBuilder().append(localIp).append(":").append(port).toString();
    }

    public static String localIp() {
        try {
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (list.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) list.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return "unknown";
    }

    public static String whoiam(Environment env) {
        Objects.requireNonNull(env, "env");
        return whoiam(Integer.valueOf(env.getProperty("server.port")));
    }
}
