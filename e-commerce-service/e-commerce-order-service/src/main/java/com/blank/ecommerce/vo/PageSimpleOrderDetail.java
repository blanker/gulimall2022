package com.blank.ecommerce.vo;

import com.blank.ecommerce.account.UserAddress;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSimpleOrderDetail {
    private List<SingleOrderItem> orderItems;
    private boolean hasMore;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SingleOrderItem{
        @Schema(name="订单ID")
        private Long id;

        private UserAddress userAddress;

        private List<SingleOrderGoodsItem> goodsItems;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SingleOrderGoodsItem {
        private SimpleGoodsInfo simpleGoodsInfo;
        private Integer count;
    }
}
