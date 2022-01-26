package com.bytehonor.sdk.netty.bytehonor.common.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.netty.bytehonor.common.util.ClasszUtils;
import com.bytehonor.sdk.netty.bytehonor.common.util.NettyJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NettyPayloadTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPayloadTest.class);

    @Test
    public void test() throws ClassNotFoundException, JsonMappingException, JsonProcessingException {

        NettyConfig vo1 = new NettyConfig();
        vo1.setBossThreads(11);

        NettyConfig vo2 = new NettyConfig();
        vo2.setBossThreads(22);

        List<NettyConfig> list = new ArrayList<NettyConfig>();
        list.add(vo1);
        list.add(vo2);

        LOG.info("1:{}", NettyJsonUtils.toJson(list));

        NettyPayload pm1 = NettyPayload.fromList(list);

        String json1 = pm1.toString();
        NettyPayload pm2 = NettyPayload.fromJson(json1);

        Class<?> cz = ClasszUtils.find(pm2.getSubject());
        LOG.info("2:{}, {}, {}", pm2.getSubject(), cz.getName(), pm2.getJson());

//        final TypeReference<List<NettyConfig>> valueTypeRef = new TypeReference<List<NettyConfig>>() {
//        };
//        List<NettyConfig> list1 = lm1.to(valueTypeRef);
//        LOG.info("3:{}", NettyJsonUtils.toJson(list1));

        List<NettyConfig> list1 = pm2.list(NettyConfig.class);
        LOG.info("4:{}", NettyJsonUtils.toJson(list1));

        NettyConfig first = list1.get(0);
        assertTrue("*test", vo1.getBossThreads() == first.getBossThreads());
    }

}
