package com.blank.ecommerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "简单的商品信息")
public class SimpleGoodsInfo {
    @Schema(description = "ID")
    private Long id;
    @Schema(description = "商品名称")
    private String goodsName;
    @Schema(description = "商品图片")
    private String goodsPic;
    @Schema(description = "价格")
    private Integer price;

    public SimpleGoodsInfo(Long id) {
        this.id = id;
    }
}
