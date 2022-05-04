package com.blank.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.account.BalanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BalanceServiceTest extends BaseTest{
    @Autowired
    private IBalanceService balanceService;

    @Test
    public void testGetCurrentUserBalanceInfo(){
        final BalanceInfo info = balanceService.getCurrentUserBalanceInfo();
        log.info(JSON.toJSONString(info));
    }

    @Test
    public void testDeductBalance(){
        BalanceInfo info = new BalanceInfo(1L, 5L);
        final BalanceInfo balanceInfo = balanceService.deductBalance(info);
        log.info(JSON.toJSONString(balanceInfo));
    }
}
