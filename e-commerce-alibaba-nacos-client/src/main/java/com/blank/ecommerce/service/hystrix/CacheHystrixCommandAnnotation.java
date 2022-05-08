package com.blank.ecommerce.service.hystrix;

import com.blank.ecommerce.service.NacosClientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CacheHystrixCommandAnnotation {
    @Autowired
    private NacosClientService nacosClientService;

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> getCache01(String serviceId) {
        log.info("get cache 01");
        return nacosClientService.getServiceInstance(serviceId);
    }

    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void cacheRemove01(String cacheId){
        log.info("remove cache 01");
    }

    public String getCacheKey(String cacheId) {
        return cacheId;
    }


    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> getCache2(@CacheKey String serviceId) {
        log.info("get cache 02");
        return nacosClientService.getServiceInstance(serviceId);
    }
    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void cacheRemove02(@CacheKey String cacheId){
        log.info("remove cache 02");
    }


    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> getCache3(String serviceId) {
        log.info("get cache 03");
        return nacosClientService.getServiceInstance(serviceId);
    }
    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void cacheRemove03(String cacheId){
        log.info("remove cache 03");
    }
}
