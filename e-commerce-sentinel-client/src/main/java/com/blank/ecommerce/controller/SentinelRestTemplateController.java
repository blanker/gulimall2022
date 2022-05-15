package com.blank.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/rest-template")
public class SentinelRestTemplateController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/get-token")
    public CommonResponse getTokenFromAuthorityService(@RequestBody UsernameAndPassword usernameAndPassword){
        String url = "http://192.168.0.108:7000/ecommerce-authority-center/authority/login";
        log.info("request token from url and body: [{}, {}]",
                url, JSON.toJSONString(usernameAndPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final CommonResponse resp = restTemplate.postForObject(url,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                CommonResponse.class
        );

        return resp;
    }
}
