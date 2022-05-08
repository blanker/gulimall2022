package com.blank.ecommerce.feign;

import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.feign.fallback.AddressClientFallback;
import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        contextId = "AddressClient",
        value = "ecommerce-account-service",
        fallback = AddressClientFallback.class
)
public interface AddressClient {

    @RequestMapping(
            value = "/ecommerce-account-service/address/address-info-by-table-id",
            method = RequestMethod.POST
    )
    CommonResponse<AddressInfo> getAddressInfoByTableId(@RequestBody TableId tableId);
}
