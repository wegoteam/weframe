package com.wegoteam.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @project: dx-scada-flink-backend
 * @description:
 * @author: XUCHANG
 * @create: 2021-11-30 11:40
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 序列化成json
     * @param value
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String value, Class<T> type) {
        T val = null;
        try {
            val = objectMapper.readValue(value, type);
        } catch (IOException e) {
            log.error("json 转换失败",e);
        }
        return val;
    }

    /**
     * 序列化成json
     * @param value
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String value, TypeReference type) {
        T val = null;
        try {
            val = (T) objectMapper.readValue(value, type);
        } catch (IOException e) {
            log.error("json 转换失败",e);
        }
        return val;
    }

    /**
     * 转换成字符串
     * @param value
     * @return
     */
    public static String toJson(Object value) {
        String valueAsString = null;
        try {
            valueAsString = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("json 转换失败",e);
        }
        return valueAsString;
    }

    /**
     * 转换成字节数组
     * @param value
     * @return
     */
    public static byte[] toJSONBytes(Object value) {
        byte[] bytes = null;
        try {
            String valueAsString = objectMapper.writeValueAsString(value);
            bytes = valueAsString.getBytes(Charset.forName("UTF-8"));
        } catch (JsonProcessingException e) {
            log.error("json 转换失败",e);
        }
        return bytes;
    }

}
