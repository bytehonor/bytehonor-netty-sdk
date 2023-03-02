package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.bytehonor.sdk.beautify.netty.common.constant.NettyConstants;

import io.netty.channel.Channel;

/**
 * @author lijianqiang
 *
 */
public class StampChannelHolder {

    private static final int CAP = NettyConstants.CAPACITY;

    /**
     * stamp channel
     */
    private static final ConcurrentHashMap<String, Channel> MAP = new ConcurrentHashMap<String, Channel>(CAP);

    public static int size() {
        return MAP.size();
    }

    public static void put(String stamp, Channel channel) {
        Objects.requireNonNull(stamp, "stamp");
        Objects.requireNonNull(channel, "channel");

        MAP.put(stamp, channel);
    }

//    public static String getStamp(Channel channel) {
//        String stamp = "";
//        if (channel == null) {
//            return stamp;
//        }
//
//        for (Entry<String, Channel> item : MAP.entrySet()) {
//            if (channel.equals(item.getValue())) {
//                stamp = item.getKey();
//                break;
//            }
//        }
//        return stamp;
//    }

    public static Channel getChannel(String stamp) {
        if (stamp == null) {
            return null;
        }

        return MAP.get(stamp);
    }

//    public static void remove(Channel channel) {
//        if (channel == null) {
//            return;
//        }
//        String stamp = getStamp(channel);
//        if (stamp != null) {
//            MAP.remove(stamp);
//        }
//    }

    public static void remove(String stamp) {
        if (stamp == null) {
            return;
        }
        MAP.remove(stamp);
    }
}
