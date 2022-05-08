package com.blank.ecommerce.feign;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.feign.fallback.GoodsClientFallback;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        contextId = "SecuredGoodsClient",
        value = "ecommerce-goods-service",
        fallback = GoodsClientFallback.class
)
public interface SecuredGoodsClient {

    @RequestMapping(
            value = "/ecommerce-goods-service/goods/simple-goods-info",
            method = RequestMethod.POST
    )
    CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId);
}
