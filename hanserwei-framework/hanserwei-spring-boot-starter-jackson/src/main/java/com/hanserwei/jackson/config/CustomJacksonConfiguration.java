package com.hanserwei.jackson.config;

import com.hanserwei.framework.common.constant.DateConstants;
import com.hanserwei.framework.common.util.JsonUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.YearMonthDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.YearMonthSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.TimeZone;

/**
 * @author hanser
 */
@AutoConfiguration
public class CustomJacksonConfiguration {

    @Bean
    public JsonMapper jsonMapper() {
        SimpleModule customModule = new SimpleModule();
        // 支持 LocalDateTime、LocalDate、LocalTime
        customModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
        customModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
        customModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateConstants.DATE_FORMAT_Y_M_D));
        customModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateConstants.DATE_FORMAT_Y_M_D));
        customModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateConstants.DATE_FORMAT_H_M_S));
        customModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateConstants.DATE_FORMAT_H_M_S));
        // 支持 YearMonth
        customModule.addSerializer(YearMonth.class, new YearMonthSerializer(DateConstants.DATE_FORMAT_Y_M));
        customModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(DateConstants.DATE_FORMAT_Y_M));


        JsonMapper jsonMapper = JsonMapper.builder()
                // 在反序列化时，忽略在 JSON 中存在但 Java 对象中不存在的属性，防止报错
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 在序列化时，允许序列化空的 POJO 类（没有属性的类），防止报错
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 设置默认时区为东八区（北京时间）
                .defaultTimeZone(TimeZone.getTimeZone("GMT+8"))
                // 添加自定义模块
                .addModule(customModule)
                // 自动查找并添加所有可用的 Jackson 模块
                .findAndAddModules()
                .build();

        // 初始化 JsonUtils中的 jsonMapper
        JsonUtils.init(jsonMapper);

        return jsonMapper;
    }
}
