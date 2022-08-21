package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

/**
 * @author lijianqiang
 *
 */
public class ChannelIdHolder {

    private final ConcurrentHashMap<String, ChannelId> map;

    public ChannelIdHolder() {
        this.map = new ConcurrentHashMap<String, ChannelId>();
    }

    public ConcurrentHashMap<String, ChannelId> getMAP() {
        return map;
    }

    public void add(ChannelId id) {
        map.put(id.asLongText(), id);
    }

    public void remove(ChannelId id) {
        map.remove(id.asLongText());
    }

    public boolean contains(ChannelId id) {
        return map.contains(id.asLongText());
    }

    public Set<ChannelId> values() {
        if (map.isEmpty()) {
            return new HashSet<ChannelId>();
        }
        Set<ChannelId> list = new HashSet<ChannelId>(map.size() * 2);
        for (Entry<String, ChannelId> item : map.entrySet()) {
            list.add(item.getValue());
        }
        return list;
    }

    public int size() {
        return map.size();
    }
}
