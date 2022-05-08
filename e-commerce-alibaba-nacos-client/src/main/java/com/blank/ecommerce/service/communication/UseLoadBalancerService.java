package com.blank.ecommerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class UseLoadBalancerService {
    @Autowired
    private RestTemplate restTemplate;

    public JwtToken getTokenByLoadBalancer(UsernameAndPassword usernameAndPassword){
        String requestUrl = String.format(
                "http://%s/ecommerce-authority-center/authority/login",
                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
        );
        log.info("LoadBalancer request url: [{}, {}]", requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        CommonResponse response = restTemplate.postForObject(
//                requestUrl,
//                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
//                CommonResponse.class);

        CommonResponse<JwtToken> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                new ParameterizedTypeReference<CommonResponse<JwtToken>>() {}
        ).getBody();
        return response.getData();

//        return restTemplate.postForObject(
//                requestUrl, ,
//                new TypeReference<CommonResponse<JwtToken>>() {}
//        );

    }

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private Environment environment;
    @Autowired
    private LoadBalancerClientFactory loadBalancerClientFactory;

    public JwtToken thinkingInLoadBalancer(UsernameAndPassword usernameAndPassword){

        String urlFormat = "http://%s/ecommerce-authority-center/authority/login";

        final List<ServiceInstance> instances = discoveryClient.getInstances(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        if (Collections.isEmpty(instances)) {
            throw new RuntimeException("cannot get ServiceInstance with serviceId: [{"+CommonConstant.AUTHORITY_CENTER_SERVICE_ID+"}]");
        }

        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        final RandomLoadBalancer loadBalancer = new RandomLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),
                name);
        loadBalancer.choose();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestUrl = null;
        CommonResponse<JwtToken> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                new ParameterizedTypeReference<CommonResponse<JwtToken>>() {}
        ).getBody();
        return response.getData();
    }
}
