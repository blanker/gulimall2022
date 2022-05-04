package com.blank.ecommerce.service.communication;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LoadBalancerConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    @LoadBalanced
//    WebClient.Builder builder() {
//        return WebClient.builder();
//    }
//
//    @Bean
//    WebClient webClient(WebClient.Builder builder) {
//        return builder.build();
//    }
}
