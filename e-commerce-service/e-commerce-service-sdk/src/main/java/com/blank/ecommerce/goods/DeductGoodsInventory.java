package com.blank.ecommerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "扣减商品库存信息")
public class DeductGoodsInventory {
    @Schema(description = "扣减商品ID")
    private Long goodsId;
    @Schema(description = "扣减商品数量")
    private Integer count;
}
