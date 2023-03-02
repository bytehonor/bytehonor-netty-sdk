package com.bytehonor.sdk.beautify.netty.common.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytehonor.sdk.beautify.netty.common.util.ClasszUtils;
import com.bytehonor.sdk.beautify.netty.common.util.NettyJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NettyPayloadsTest {

    private static final Logger LOG = LoggerFactory.getLogger(NettyPayloadsTest.class);

    @Test
    public void test() throws ClassNotFoundException, JsonMappingException, JsonProcessingException {

        NettyClientConfig vo1 = new NettyClientConfig();
        vo1.setClientThreads(11);

        NettyClientConfig vo2 = new NettyClientConfig();
        vo2.setClientThreads(22);

        List<NettyClientConfig> list = new ArrayList<NettyClientConfig>();
        list.add(vo1);
        list.add(vo2);

        LOG.info("1:{}", NettyJsonUtils.toJson(list));

        NettyPayloads pm1 = NettyPayloads.fromList(list);

        String json1 = pm1.toString();
        NettyPayloads pm2 = NettyPayloads.fromJson(json1);

        Class<?> cz = ClasszUtils.find(pm2.getSubject());
        LOG.info("2: subject:{}, name:{}, body:{}", pm2.getSubject(), cz.getName(), pm2.getBody());

//        final TypeReference<List<NettyConfig>> valueTypeRef = new TypeReference<List<NettyConfig>>() {
//        };
//        List<NettyConfig> list1 = lm1.to(valueTypeRef);
//        LOG.info("3:{}", NettyJsonUtils.toJson(list1));

        List<NettyClientConfig> list1 = pm2.list(NettyClientConfig.class);
        LOG.info("4:{}", NettyJsonUtils.toJson(list1));

        NettyClientConfig first = list1.get(0);
        assertTrue("*test", vo1.getClientThreads() == first.getClientThreads());
    }

    @Test
    public void test2() {
        String text = "hello world";
        NettyPayloads np = NettyPayloads.fromOne(text);
        LOG.info("test2 json:{}", NettyJsonUtils.toJson(np));

        String src = np.one(String.class);
        LOG.info("test2 src:{}", src);

        assertTrue("*test2", text.equals(src));
    }
}
