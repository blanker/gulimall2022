package com.blank.ecommerce;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.conf.DataSourceProxyAutoConfiguration;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing()
@Import(DataSourceProxyAutoConfiguration.class)
public class GoodsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GoodsApplication.class, args);
        String[] beanNames = context.getBeanNamesForType(DataSource.class);
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            log.info("datasource bean: [{} => {}]", bean, bean.getClass().getCanonicalName());
        }
        String[] beanNamesForType = context.getBeanNamesForType(DataSourceProxy.class);
        log.info("DataSourceProxy: [{}]", JSON.toJSONString(beanNamesForType));

    }
}


