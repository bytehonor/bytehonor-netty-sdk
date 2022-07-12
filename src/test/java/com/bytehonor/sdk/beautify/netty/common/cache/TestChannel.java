package com.bytehonor.sdk.beautify.netty.common.cache;

import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.ChannelId;

public class TestChannel implements ChannelId {

    private static final long serialVersionUID = 4631074233021497714L;

    private static final AtomicLong AL = new AtomicLong(0);

    private final String id;

    public TestChannel() {
        this.id = "id:" + AL.incrementAndGet() + ":" + System.nanoTime();
    }

    @Override
    public int compareTo(ChannelId o) {
        return 0;
    }

    @Override
    public String asShortText() {
        return id;
    }

    @Override
    public String asLongText() {
        return id;
    }

}
