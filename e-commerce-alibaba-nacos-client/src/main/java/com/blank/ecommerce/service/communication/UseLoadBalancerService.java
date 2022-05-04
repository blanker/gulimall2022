package com.blank.ecommerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UseLoadBalancerService {
    @Autowired
    private RestTemplate restTemplate;

    public String getTokenByLoadBalancer(UsernameAndPassword usernameAndPassword){
        String requestUrl = String.format(
                "http://%s/ecommerce-authority-center/authority/login",
                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
        );
        log.info("LoadBalancer request url: [{}, {}]", requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CommonResponse response = restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                CommonResponse.class);
        return JSON.toJSONString(response);

//        return restTemplate.postForObject(
//                requestUrl, ,
//                new TypeReference<CommonResponse<JwtToken>>() {}
//        );

    }
}
