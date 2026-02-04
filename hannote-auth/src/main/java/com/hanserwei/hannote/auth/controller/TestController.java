package com.hanserwei.hannote.auth.controller;

import com.hanserwei.framework.biz.operationlog.aspect.ApiOperationLog;
import com.hanserwei.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author hanser
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperationLog
    public Response<String> test() {
        return Response.success("Hello, Hanserwei!");
    }

    @GetMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2() {
        return Response.success(User.builder()
                .nickName("Hanserwei")
                .createTime(LocalDateTime.now())
                .build());
    }

    @PostMapping("/test3")
    @ApiOperationLog(description = "测试接口3")
    public Response<User> test2(@RequestBody User user) {
        return Response.success(user);
    }
}