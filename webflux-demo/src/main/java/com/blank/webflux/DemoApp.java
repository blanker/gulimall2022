package com.blank.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}
