package com.blank.ecommerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UseRestTemplateService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public JwtToken getTokenFromAuthorityCenter(UsernameAndPassword usernameAndPassword){
        String requestUrl = "http://localhost:7000/ecommerce-authority-center/authority/login";
        log.info("RestTemplate request url: [{}, {}]", requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new RestTemplate().postForObject(
                requestUrl, new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                JwtToken.class
        );

    }

    public JwtToken getTokenWithLoadBalancer(UsernameAndPassword usernameAndPassword){
        final ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos Client Info: [{}, {}, {}]", serviceInstance.getInstanceId(), serviceInstance.getServiceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));

        String requestUrl = String.format(
                "http://%s:%s/ecommerce-authority-center/authority/login",
                serviceInstance.getHost(),
                serviceInstance.getPort()
        );
        log.info("LoadBalancer request url: [{}, {}]", requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new RestTemplate().postForObject(
                requestUrl, new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                JwtToken.class
        );

    }
}
