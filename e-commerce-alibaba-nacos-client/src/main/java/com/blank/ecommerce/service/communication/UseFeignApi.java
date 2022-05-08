package com.blank.ecommerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UseFeignApi {
    @Autowired
    private DiscoveryClient discoveryClient;

    public JwtToken thinkInFeign(UsernameAndPassword usernameAndPassword) throws Exception {
        String serviceId = null;
        Annotation[] annotations = AuthorityFeignClient.class.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(FeignClient.class)) {
                serviceId = ((FeignClient)annotation).value();
                log.info("get serviceId from AuthorityFeignClient: [{}]", serviceId);
                break;
            }
        }
        if (null == serviceId) {
            throw new RuntimeException("FeignClient invalid.");
        }

        final List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (Collections.isEmpty(instances)) {
            throw new RuntimeException("cannot get ServiceInstance with serviceId: [{"+serviceId+"}]");
        }

        final ServiceInstance serviceInstance = instances.get(new Random().nextInt(instances.size()));
        log.info("get service instance: [{}, {}, {}]", serviceInstance.getInstanceId(), serviceInstance.getHost(),
                JSON.toJSONString(serviceInstance.getPort()));

        AuthorityFeignClient feignClient = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logLevel(Logger.Level.FULL)
                .contract(new SpringMvcContract())
                .target(
                        AuthorityFeignClient.class,
                        String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort())
                );
        return feignClient.token(usernameAndPassword).getData();
    }
}
