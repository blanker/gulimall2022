package com.blank.ecommerce;

import com.alibaba.fastjson.JSON;
import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing()
public class GoodsApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    @Autowired
    private SeataProperties seataProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(JSON.toJSONString(seataProperties));
    }
}


