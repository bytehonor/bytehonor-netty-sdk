package com.bytehonor.sdk.netty.bytehonor.common.model;

import java.util.Objects;

import io.netty.channel.ChannelId;

public class NettyChannel {
    private ChannelId id;
    private String longText;

    public static NettyChannel of(ChannelId id) {
        Objects.requireNonNull(id, "id");
        NettyChannel model = new NettyChannel();
        model.setId(id);
        model.setLongText(id.asLongText());
        return model;
    }

    public ChannelId getId() {
        return id;
    }

    public void setId(ChannelId id) {
        this.id = id;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

}
