package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyEnvUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyEnvUtils.class);

    public static String whoiam(int port) {
        return new StringBuilder().append(localIp()).append(":").append(port).toString();
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
        return "unkown";
    }
}
