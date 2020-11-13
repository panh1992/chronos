package org.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.core.util.jackson.InstantDeserializer;
import org.core.util.jackson.InstantSerializer;
import org.core.util.jackson.NumberSerializer;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * 通用帮助类
 *
 * @author panhong
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

    /**
     * 获取系统 jackson 配置的 ObjectMapper 信息
     */
    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 禁用空对象转换json校验
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // ==========  序列化/反序列化 处理  ==============
        SimpleModule instantModule = new SimpleModule("instant");
        instantModule.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        instantModule.addDeserializer(Instant.class, InstantDeserializer.INSTANCE);
        mapper.registerModule(instantModule);

        SimpleModule numberModule = new SimpleModule("number");
        numberModule.addSerializer(Number.class, NumberSerializer.INSTANCE);
        mapper.registerModule(numberModule);

        return mapper;
    }

    /**
     * 将对象转换为json字符串
     */
    public static String toJson(Object obj) {
        ObjectMapper mapper = getObjectMapper();
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return mapper.createObjectNode().toString();
        }
    }

    /**
     * 将数据转换为 byte 数组
     *
     * @param data 待转换字符串
     * @return byte 数组
     */
    public static byte[] convertBytes(String data) {

        return data.getBytes(StandardCharsets.UTF_8);

    }

}
