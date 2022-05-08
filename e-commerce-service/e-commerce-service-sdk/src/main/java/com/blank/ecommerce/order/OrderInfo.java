package com.blank.ecommerce.order;

import com.blank.ecommerce.goods.DeductGoodsInventory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="用户发起购买订单")
public class OrderInfo {
    private Long userAddress;
    private List<OrderItem> orderItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name="订单商品信息")
    public static class OrderItem {
        private Long goodsId;
        private Integer count;

        public DeductGoodsInventory toDeductGoodsInventory(){
            return new DeductGoodsInventory(goodsId, count);
        }
    }
}
