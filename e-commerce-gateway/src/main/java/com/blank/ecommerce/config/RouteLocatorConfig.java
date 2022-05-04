package com.blank.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RouteLocatorConfig {
    @Value("${server.port}")
    private Integer port;
//    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path(
                                "/ecommerce/login",
                                "/ecommerce/register"
                        ).uri("http://localhost:"+port))
                .build();
    }
}
