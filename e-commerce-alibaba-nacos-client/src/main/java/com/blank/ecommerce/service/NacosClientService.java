package com.blank.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class NacosClientService {
    private final DiscoveryClient discoveryClient;

    public NacosClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public List<ServiceInstance> getServiceInstance(String serviceId){
        log.info("request service instance: [{}]", serviceId);
//        log.info("mock error [{}]", 1/0);
//        try {
////            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return discoveryClient.getInstances(serviceId);
    }

    public List<List<ServiceInstance>> getServiceInstance(List<String> serviceIds){
        log.info("request service instances: [{}]", serviceIds);
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());
        serviceIds.forEach( serviceId ->
            result.add(discoveryClient.getInstances(serviceId))
        );
        return result;
    }

    @HystrixCollapser(
            batchMethod="findNacosClientInfos",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    @HystrixProperty(name="timerDelayInMilliseconds", value="300")
            }
    )
    public Future<List<ServiceInstance>> findNacosClientInfo(String serviceId) {
        throw new RuntimeException("This method should not executed!");
    }

    @HystrixCommand
    public List<List<ServiceInstance>> findNacosClientInfos(List<String> serviceIds) {
        log.info("come in find nacos client infos: [{}]", JSON.toJSONString(serviceIds));
        return getServiceInstance(serviceIds);
    }
}
