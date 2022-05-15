package com.blank.ecommerce.conf;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class SentinelConfig {

    @Bean
    @SentinelRestTemplate(
            blockHandler = "handleBlock",
            blockHandlerClass = RestTemplateExceptionUtil.class,
            fallback = "handleFallback",
            fallbackClass = RestTemplateExceptionUtil.class
    )
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
