package com.bytehonor.sdk.beautify.netty.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyEnvUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyEnvUtils.class);

    private static String LOCAL_IP = "";

    public static String whoiam(int port) {
        return whoiam(localIp(), port);
    }

    public static String whoiam(String name, int port) {
        return new StringBuilder().append(name).append(":").append(port).toString();
    }

    public static String localIp() {
        if (LOCAL_IP != null && LOCAL_IP.isEmpty() == false) {
            return LOCAL_IP;
        }
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
                            LOCAL_IP = ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("error", e);
            LOCAL_IP = "unknown";
        }
        return LOCAL_IP;
    }
}
