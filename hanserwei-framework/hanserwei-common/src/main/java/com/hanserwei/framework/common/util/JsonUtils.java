package com.hanserwei.framework.common.util;

import com.hanserwei.framework.common.constant.DateConstants;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * JSON 工具类，提供对象与 JSON 字符串之间的转换功能
 *
 * @author hanser
 */
public class JsonUtils {

    /**
     * 初始化 Jackson 的 JsonMapper 实例
     */
    private static final JsonMapper JSON_MAPPER;

    static {
        // 创建日期时间格式化器，使用 yyyy-MM-dd HH:mm:ss 格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateConstants.Y_M_D_H_M_S_FORMAT);

        // 创建自定义模块，用于注册 LocalDateTime 的序列化和反序列化器
        SimpleModule customizeModule = new SimpleModule();
        // 注册 LocalDateTime 反序列化器，将 JSON 字符串转换为 LocalDateTime 对象
        customizeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        // 注册 LocalDateTime 序列化器，将 LocalDateTime 对象转换为 JSON 字符串
        customizeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        // 构建 JsonMapper 实例
        JSON_MAPPER = JsonMapper.builder()
                // 在反序列化时，忽略在 JSON 中存在但 Java 对象中不存在的属性，防止报错
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 在序列化时，允许序列化空的 POJO 类（没有属性的类），防止报错
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 设置默认时区为东八区（北京时间）
                .defaultTimeZone(TimeZone.getTimeZone("GMT+8"))
                // 添加自定义模块
                .addModule(customizeModule)
                // 自动查找并添加所有可用的 Jackson 模块
                .findAndAddModules()
                .build();
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
