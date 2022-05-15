package com.blank.ecommerce.feign;

import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {
    @Override
    public CommonResponse<String> getResultByFeign(Integer code) {
        log.error("request with feign error");
        return new CommonResponse(
                -1,
                "sentinel feign client fallback",
                "some error occur： " +code
        );
    }
}
