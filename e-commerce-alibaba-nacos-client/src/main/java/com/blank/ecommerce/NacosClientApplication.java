package com.blank.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class NacosClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }

//    @Configuration
//    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests().anyRequest().permitAll()
//                    .and().csrf().disable();
//        }
//    }
}
