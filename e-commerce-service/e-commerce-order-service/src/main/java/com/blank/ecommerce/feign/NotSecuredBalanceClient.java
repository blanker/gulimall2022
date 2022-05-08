package com.blank.ecommerce.feign;

import com.blank.ecommerce.account.BalanceInfo;
import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        contextId = "NotSecuredBalanceClient",
        value = "ecommerce-account-service"
)
public interface NotSecuredBalanceClient {

    @RequestMapping(
            value="/ecommerce-account-service/balance/deduct-balance",
            method = RequestMethod.PUT
    )
    CommonResponse<BalanceInfo> deductBalance(@RequestBody BalanceInfo balanceInfo);
}
