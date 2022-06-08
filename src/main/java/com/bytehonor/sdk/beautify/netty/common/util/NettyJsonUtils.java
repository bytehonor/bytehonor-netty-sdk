package com.bytehonor.sdk.beautify.netty.common.util;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class NettyJsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettyJsonUtils.class);

    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();

    private static final TypeFactory FACTORY = JACKSON_MAPPER.getTypeFactory();

    private static final ConcurrentHashMap<String, CollectionType> TYPES = new ConcurrentHashMap<String, CollectionType>(
            1024);

    static {
        JACKSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        Objects.requireNonNull(json, "json");
        Objects.requireNonNull(valueType, "valueType");
        try {
            return JACKSON_MAPPER.readValue(json, valueType);
        } catch (Exception e) {
            LOG.error("json:{}, error", json, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        Objects.requireNonNull(json, "json");
        Objects.requireNonNull(valueTypeRef, "valueTypeRef");
        try {
            return JACKSON_MAPPER.readValue(json, valueTypeRef);
        } catch (Exception e) {
            LOG.error("json:{}, error", json, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromListJson(String json, Class<T> valueType) {
        Objects.requireNonNull(json, "json");
        Objects.requireNonNull(valueType, "valueType");
        try {
            CollectionType ct = listType(valueType);
            return JACKSON_MAPPER.readValue(json, ct);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CollectionType listType(Class<?> valueType) {
        Objects.requireNonNull(valueType, "valueType");
        CollectionType ct = TYPES.get(valueType.getName());
        if (ct != null) {
            return ct;
        }
        ct = FACTORY.constructCollectionType(List.class, valueType);
        if (ct != null) {
            TYPES.put(valueType.getName(), ct);
        }
        return ct;
    }

    public static String toJson(Object value) {
        Objects.requireNonNull(value, "value");
        try {
            return JACKSON_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            LOG.error("toJson", e);
            throw new RuntimeException(e);
        }
    }
}
