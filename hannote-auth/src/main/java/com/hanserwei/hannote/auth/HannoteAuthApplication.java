package com.hanserwei.hannote.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanser
 */
@SpringBootApplication
@MapperScan("com.hanserwei.hannote.auth.domain.mapper")
public class HannoteAuthApplication {
    static void main() {
        SpringApplication.run(HannoteAuthApplication.class);
    }
}
