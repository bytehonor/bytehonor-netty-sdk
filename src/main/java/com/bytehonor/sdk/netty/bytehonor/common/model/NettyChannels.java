package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NettyChannels {

    private List<NettyChannel> list;

    public NettyChannels() {
        this.list = Collections.synchronizedList(new ArrayList<NettyChannel>());
    }

    public List<NettyChannel> getList() {
        return list;
    }

    public void setList(List<NettyChannel> list) {
        this.list = list;
    }

    public void add(NettyChannel channel) {
        if (channel == null) {
            return;
        }
        this.list.add(channel);
    }

    public void remove(String longText) {
        if (longText == null) {
            return;
        }
        if (list.size() < 1) {
            return;
        }
        Iterator<NettyChannel> it = list.iterator();
        while (it.hasNext()) {
            if (longText.equals(it.next().getLongText())) {
                it.remove();
            }
        }
    }

    public boolean exists(String longText) {
        if (longText == null) {
            return false;
        }
        if (list.size() < 1) {
            return false;
        }
        boolean has = false;
        for (NettyChannel channel : list) {
            if (longText.equals(channel.getLongText())) {
                has = true;
                break;
            }
        }
        return has;
    }

    public void removeAll() {
        list.clear();
    }
    
    public int size() {
        return list.size();
    }

}
