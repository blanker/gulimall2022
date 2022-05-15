package com.blank.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.blank.ecommerce.block_handler.CommonBlockHandler;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class RateLimitController {

    @GetMapping("/by-resource")
    @SentinelResource(
            value="byResource",
            blockHandler = "blockHandler",
            blockHandlerClass = CommonBlockHandler.class)
    public CommonResponse<String> byResource(){
        log.info("by resource");
        return new CommonResponse<>(1, "by resource", "by resource");
    }

    @GetMapping("/by-url")
    @SentinelResource(value="byUrl")
    public CommonResponse<String> byUrl(){
        log.info("by url");
        return new CommonResponse<>(1, "by url", "by url");
    }
}
