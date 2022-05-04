package com.blank.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthorityCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
