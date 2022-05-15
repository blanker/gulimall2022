package com.blank.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.fallback_handler.CommonFallbackHandler;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/sentinel-fallback")
public class SentinelFallbackController {
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/get-token")
    @SentinelResource(
            value="getTokenFromAuthorityService",
            fallback = "getTokenFromAuthorityServiceFallback",
            fallbackClass = CommonFallbackHandler.class)
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

    @GetMapping("/ignore-exception")
    @SentinelResource(
            value = "ignoreException",
            fallback = "ignoreExceptionFallback",
            fallbackClass =CommonFallbackHandler.class,
            exceptionsToIgnore = NullPointerException.class
    )
    public CommonResponse<JwtToken> ignoreException(@RequestParam Integer code) {
        if (code % 2 == 0) {
            throw new NullPointerException("error code is : " + code);
        }
        return new CommonResponse<>(1, "",
                new JwtToken("token"));
    }
}
