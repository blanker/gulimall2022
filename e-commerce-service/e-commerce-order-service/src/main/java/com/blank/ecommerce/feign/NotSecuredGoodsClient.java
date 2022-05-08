package com.blank.ecommerce.feign;

import com.blank.ecommerce.account.BalanceInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        contextId = "NotSecuredGoodsClient",
        value = "ecommerce-goods-service"
)
public interface NotSecuredGoodsClient {

    @RequestMapping(
            value="/ecommerce-goods-service/goods/deduct-goods-inventory",
            method = RequestMethod.PUT
    )
    CommonResponse<Boolean> deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories);

    @RequestMapping(
            value = "/ecommerce-goods-service/goods/simple-goods-info",
            method = RequestMethod.POST
    )
    CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId);
}
