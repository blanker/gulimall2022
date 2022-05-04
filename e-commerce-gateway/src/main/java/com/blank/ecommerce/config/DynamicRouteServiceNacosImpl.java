package com.blank.ecommerce.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@DependsOn("gatewayConfig")
public class DynamicRouteServiceNacosImpl {
    private ConfigService configService;
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    public void initConfigService(){
        Properties props = new Properties();
        try {
            configService =  NacosFactory.createConfigService(props);
        } catch (NacosException e) {
            e.printStackTrace();
            configService = null;
        }
    }
}
