package com.blank.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class WebfluxDemoController {
    @GetMapping("/demo-webflux")
    public Mono<String> demo(){
        log.info("WebFluxDemoController-{}", "hello webflux");
        return Mono.just("Hello, webflux");
    }
}
