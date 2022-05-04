package com.blank.ecommerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "详细的商品信息")
public class GoodsInfo {
    @Schema(description = "ID")
    private Long id;
    @Schema(description = "商品分类")
    private String goodsCategory;
    @Schema(description = "品牌分类")
    private String brandCategory;
    @Schema(description = "商品名称")
    private String goodsName;
    @Schema(description = "商品图片")
    private String goodsPic;
    @Schema(description = "商品描述")
    private String goodsDescription;
    @Schema(description = "商品状态")
    private Integer goodsStatus;
    @Schema(description = "价格")
    private Integer price;
    @Schema(description = "总供应量")
    private Long supply;
    @Schema(description = "库存")
    private Long inventory;
    @Schema(description = "商品属性")
    private GoodsProperty goodsProperty;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "更新时间")
    private Date updateTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "商品属性")
    public static class GoodsProperty{
        @Schema(description = "尺寸")
        private String size;
        @Schema(description = "颜色")
        private String color;
        @Schema(description = "材质")
        private String material;
        @Schema(description = "图案")
        private String pattern;
    }
}
