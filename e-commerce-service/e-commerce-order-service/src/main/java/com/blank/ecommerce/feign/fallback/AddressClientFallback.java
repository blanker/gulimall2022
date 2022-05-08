package com.blank.ecommerce.feign.fallback;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.feign.AddressClient;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class AddressClientFallback implements AddressClient {
    @Override
    public CommonResponse<AddressInfo> getAddressInfoByTableId(TableId tableId) {
        log.error("[address client feign request error in order service], get adress info by table id. [{}]", JSON.toJSONString(tableId));
        return new CommonResponse<>(-1,
                "[goods client feign request error in order service]",
                new AddressInfo(-1L, Collections.emptyList())
        );
    }
}
