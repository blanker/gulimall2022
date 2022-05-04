package com.blank.gulimall.thirdparty;

import com.alibaba.cloud.spring.boot.context.autoconfigure.EdasContextAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {EdasContextAutoConfiguration.class})
public class GulimallThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallThirdPartyApplication.class);
    }
}
