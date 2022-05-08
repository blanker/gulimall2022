package com.blank.ecommerce.controller;

import com.blank.ecommerce.service.communication.AuthorityFeignClient;
import com.blank.ecommerce.service.communication.UseFeignApi;
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
    public JwtToken getTokenWithLoadBalancer(@RequestBody UsernameAndPassword usernameAndPassword){
        return useRestTemplateService.getTokenWithLoadBalancer(usernameAndPassword);
    }

    @PostMapping("/load-balancer")
    public JwtToken getTokenByLoadBalancer(@RequestBody UsernameAndPassword usernameAndPassword){
        return useLoadBalancerService.getTokenByLoadBalancer(usernameAndPassword);
    }

    @PostMapping("/token-by-feign")
    public CommonResponse<JwtToken> getTokenByFeign(
            @RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        return authorityFeignClient.token(usernameAndPassword);
    }

    @Autowired
    private AuthorityFeignClient authorityFeignClient;
    @Autowired
    private UseFeignApi useFeignApi;

    @PostMapping("/thinking-in-feign")
    public JwtToken thinkingInFeign(
            @RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        return useFeignApi.thinkInFeign(usernameAndPassword);
    }
}
