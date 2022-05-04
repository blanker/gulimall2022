package com.blank.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Autowired
    private DemoHandler demoHandler;

    @Bean
    public RouterFunction<ServerResponse> demoRouter() {
        return route()
                .GET("/hello", demoHandler::hello)
                .GET("/world", demoHandler::world)
                .GET("/times", demoHandler::times)
                .build();
    }
}
