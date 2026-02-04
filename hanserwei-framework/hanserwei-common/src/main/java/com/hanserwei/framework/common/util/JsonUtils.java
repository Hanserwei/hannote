package com.hanserwei.framework.common.util;

import tools.jackson.databind.json.JsonMapper;

/**
 * JSON 工具类，提供对象与 JSON 字符串之间的转换功能
 *
 * @author hanser
 */
public class JsonUtils {

    /**
     * 初始化 Jackson 的 JsonMapper 实例
     */
    private static JsonMapper JSON_MAPPER = new JsonMapper();

    /**
     * 初始化：统一使用 Spring Boot 个性化配置的 ObjectMapper
     *
     * @param jsonMapper 自定义的 JsonMapper 实例
     */
    public static void init(JsonMapper jsonMapper) {
        JSON_MAPPER = jsonMapper;
    }

    /**
     * 将对象序列化为 JSON 字符串
     *
     * @param object 需要转换的对象
     * @return 转换后的 JSON 字符串
     */
    public static String toJsonString(Object object) {
        return JSON_MAPPER.writeValueAsString(object);
    }
}
