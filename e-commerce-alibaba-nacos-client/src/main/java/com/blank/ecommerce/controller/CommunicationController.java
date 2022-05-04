package com.blank.ecommerce.controller;

import com.blank.ecommerce.service.communication.UseLoadBalancerService;
import com.blank.ecommerce.service.communication.UseRestTemplateService;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.JwtToken;
import com.blank.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/communication")
public class CommunicationController {
    @Autowired
    private UseRestTemplateService useRestTemplateService;
    @Autowired
    private UseLoadBalancerService useLoadBalancerService;

    @PostMapping("/rest-template")
    public JwtToken getTokenFromAuthorityCenter(@RequestBody UsernameAndPassword usernameAndPassword) {
        return useRestTemplateService.getTokenFromAuthorityCenter(usernameAndPassword);
    }

    @PostMapping("/rest-template-load-balancer")
    public JwtToken getTokenWithLoadBalancer(UsernameAndPassword usernameAndPassword){
        return useRestTemplateService.getTokenWithLoadBalancer(usernameAndPassword);
    }

    @PostMapping("/load-balancer")
    public String getTokenByLoadBalancer(UsernameAndPassword usernameAndPassword){
        return useLoadBalancerService.getTokenByLoadBalancer(usernameAndPassword);
    }
}
