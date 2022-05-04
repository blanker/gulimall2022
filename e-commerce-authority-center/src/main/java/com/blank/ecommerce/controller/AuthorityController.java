package com.blank.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.annotation.IgnoreResponseAdvice;
import com.blank.ecommerce.service.IJWTService;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private IJWTService jwtService;

    @PostMapping("/login")
//    @IgnoreResponseAdvice
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to get token with param: [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(
                jwtService.generateToken(
                        usernameAndPassword.getUsername(),
                        usernameAndPassword.getPassword()
                )
        );
    }

    @PostMapping("/register")
//    @IgnoreResponseAdvice
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to register with param: [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(jwtService.registerUserAndGenerateToken(usernameAndPassword));
    }
}