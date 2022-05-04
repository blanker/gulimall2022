package com.blank.ecommerce.controller;

import com.blank.ecommerce.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {
    @Autowired
    private NacosClientService nacosClientService;

    @RequestMapping("/service-instance")
    public List<ServiceInstance> getServiceInstancesById(
            @RequestParam(value="serviceId", defaultValue = "e-commerce-nacos-client") String serviceId
    ){
        log.info("come in getServiceInstancesById: [{}]", serviceId);
        return nacosClientService.getServiceInstance(serviceId);
    }

}
