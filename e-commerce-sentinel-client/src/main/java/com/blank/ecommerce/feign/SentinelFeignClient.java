package com.blank.ecommerce.feign;

import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "not-exists-service",
        fallback = SentinelFeignClientFallback.class
)
public interface SentinelFeignClient {

    @RequestMapping(
            value = "not-exist",
            method = RequestMethod.GET
    )
    CommonResponse<String> getResultByFeign(@RequestParam("code") Integer code);
}
