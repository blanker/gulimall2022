package com.blank.ecommerce;

import com.alibaba.fastjson.JSON;
import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class GoodsApplicationTests implements ApplicationRunner {
    @Test
    public void contextLoad(){}

    @Autowired
    private SeataProperties seataProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(JSON.toJSONString(seataProperties));
    }
}
