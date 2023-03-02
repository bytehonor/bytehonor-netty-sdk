package com.bytehonor.sdk.beautify.netty.common.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.util.ClasszUtils;
import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NettyPayloadTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPayloadTest.class);

    @Test
    public void test() throws ClassNotFoundException, JsonMappingException, JsonProcessingException {

        SubscribeRequest request = SubscribeRequest.yes("test");

        LOG.info("1:{}", NettyJsonUtils.toJson(request));

        NettyPayload pm1 = NettyPayload.transfer(request);

        String json1 = pm1.toString();
        NettyPayload pm2 = NettyJsonUtils.fromJson(json1, NettyPayload.class);

        Class<?> cz = ClasszUtils.find(pm2.getSubject());
        LOG.info("2: subject:{}, name:{}, body:{}", pm2.getSubject(), cz.getName(), pm2.getBody());

        SubscribeRequest request2 = pm2.reflect(SubscribeRequest.class);
        assertTrue("*test", request.getSubject().equals(request2.getSubject()));
    }

    @Test
    public void test2() {
        String text = "hello world";
        NettyPayload np = NettyPayload.transfer(text);
        LOG.info("test2 json:{}", NettyJsonUtils.toJson(np));

        String src = np.reflect(String.class);
        LOG.info("test2 src:{}", src);

        assertTrue("*test2", text.equals(src));
    }

    
}
