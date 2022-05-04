package com.blank.ecommerce.controller;

import com.blank.ecommerce.account.BalanceInfo;
import com.blank.ecommerce.service.IBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name="用户地址服务", description = "用户地址服务")
@RequestMapping("/balance")
public class BalanceController {
    @Autowired private IBalanceService balanceService;

    @Operation(description = "获取当前用户账户余额", method = "GET")
    @GetMapping("/current-balance")
    public BalanceInfo getCurrentUserBalanceInfo(){
        return balanceService.getCurrentUserBalanceInfo();
    }

    @Operation(description = "扣减用户账户余额", method = "PUT")
    @PutMapping("/deduct-balance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo){
        return balanceService.deductBalance(balanceInfo);
    }
}
