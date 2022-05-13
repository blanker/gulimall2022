package com.blank.ecommerce;

import com.blank.ecommerce.conf.DataSourceProxyAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
@Import(DataSourceProxyAutoConfiguration.class)
public class OrderApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderApplication.class, args);
        String[] beanNames = context.getBeanNamesForType(DataSource.class);
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            log.info("datasource bean: [{} => {}]", bean, bean.getClass().getCanonicalName());
        }
    }
}
