package com.blank.ecommerce;

import com.blank.ecommerce.conf.DataSourceProxyAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class AccountApplication {
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(AccountApplication.class);
        final ConfigurableApplicationContext context = app.run(args);
        log.info("application started.");

        String[] dataSourceNames = context.getBeanNamesForType(DataSource.class);
        for (String dataSourceName : dataSourceNames) {
            log.info("data source: [{}, {}]", dataSourceName, context.getBean(dataSourceName));
        }
    }
}
