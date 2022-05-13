package com.blank.ecommerce;

import com.blank.ecommerce.conf.DataSourceProxyAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
@Import(DataSourceProxyAutoConfiguration.class)
public class AccountApplication {
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(AccountApplication.class);
        final ConfigurableApplicationContext context = app.run(args);
        log.info("application started.");
    }
}
