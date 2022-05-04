package com.blank.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    public static final long DEFAULT_TIMEOUT = 30000;
    @Value("{spring.cloud.nacos.discovery.server-addr}")
    public static String NACOS_SERVER_ADDR;
    @Value("{spring.cloud.nacos.discovery.namespace}")
    public static String NACOS_NAMESPACE;
    public static String NACOS_ROUTE_DATA_ID;
    public static String NACOS_ROUTE_GROUP;
}
