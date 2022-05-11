package com.blank.ecommerce.feign;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor headerInterceptor(){
        return template -> {
            final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                final HttpServletRequest request = attributes.getRequest();
                final Enumeration<String> headerNames = request.getHeaderNames();
                if (null != headerNames) {
                    while (headerNames.hasMoreElements()) {
                        final String name = headerNames.nextElement();
                        final String value = request.getHeader(name);
                        if (!name.equalsIgnoreCase("content-length")) {
                            template.header(name, value);
                        }
                    }
                }
            }
        };
    }
}
