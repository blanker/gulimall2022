package com.blank.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/code")
public class FlowRuleCodeController {

    @PostConstruct
    public void init(){
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        flowRule.setResource("flowRuleCode");
        flowRule.setCount(1);
        flowRules.add(flowRule);

        FlowRuleManager.loadRules(flowRules);
    }

    @GetMapping("/flow-rule")
    @SentinelResource(value = "flowRuleCode", blockHandler = "handleException")
//    @SentinelResource("flowRuleCode")
    public CommonResponse<String> flowRuleCode(){
        log.info("request flow-rule");
        return new CommonResponse(0, "", "flow rule code");
    }

    public CommonResponse<String> handleException(BlockException blockException) {
        log.error("has block exception: [{}]", JSON.toJSONString(blockException.getRule()));
        return new CommonResponse<>(-1,
                "flow rule exception",
                blockException.getClass().getCanonicalName());
    }
}
