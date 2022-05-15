package com.blank.ecommerce.controller;

import com.blank.ecommerce.feign.SentinelFeignClient;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sentinel-feign")
public class SentinelFeignController {
    @Autowired
    private SentinelFeignClient sentinelFeignClient;

    @GetMapping("/result-by-feign")
    public CommonResponse<String> getResultByFeign(@RequestParam("code") Integer code){
        return sentinelFeignClient.getResultByFeign(code);
    }
}
