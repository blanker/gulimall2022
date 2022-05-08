package com.blank.ecommerce.service.communication;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;


@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(
                100,
                SECONDS.toMicros(1L),
                5
        );
    }

    public static final int CONNECT_TIMEOUT_MILLS = 5000;
    public static final int READ_TIMEOUT_MILLS = 5000;

    @Bean
    public Request.Options feignRequestOptions(){
        return  new Request.Options(
                CONNECT_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,
                READ_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,
                true
        );
    }
}
