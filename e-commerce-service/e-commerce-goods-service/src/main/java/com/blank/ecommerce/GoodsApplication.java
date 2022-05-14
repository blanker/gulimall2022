package com.blank.ecommerce;

import com.alibaba.cloud.seata.feign.SeataFeignClient;
import com.alibaba.fastjson.JSON;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing()
public class GoodsApplication implements ApplicationRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GoodsApplication.class, args);
        String[] beanNames = context.getBeanNamesForType(DataSource.class);
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            log.info("datasource bean: [{} => {}]", bean, bean.getClass().getCanonicalName());
        }
        String[] beanNamesForType = context.getBeanNamesForType(DataSourceProxy.class);
        log.info("DataSourceProxy: [{}]", JSON.toJSONString(beanNamesForType));

        String[] bns = context.getBeanNamesForType(SeataFeignClient.class);
        for (String bn : bns) {
            log.info("SeataFeignClient: [{}]", context.getBean(bn));
        }

        log.info("WebMvcConfigurationSupport: [{}]",context.getBean(WebMvcConfigurationSupport.class));
        String[] names = context.getBeanNamesForType(WebMvcConfigurer.class);
        log.info("WebMvcConfigurer: [{}]", names.length);
        for (String name : names) {
            log.info("[{} => {}]", name, context.getBean(name));
        }

    }

    @Autowired
    private SeataProperties seataProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(JSON.toJSONString(seataProperties));
    }
}


