package com.blank.ecommerce.feign.fallback;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.feign.SecuredGoodsClient;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class GoodsClientFallback implements SecuredGoodsClient {
    @Override
    public CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(TableId tableId) {
        log.error("[goods client feign request error in order service], get simple goods. [{}]", JSON.toJSONString(tableId));
        return new CommonResponse<>(-1, "[goods client feign request error in order service]", Collections.emptyList());
    }
}
