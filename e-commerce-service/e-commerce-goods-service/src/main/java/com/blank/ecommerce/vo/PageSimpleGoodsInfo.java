package com.blank.ecommerce.vo;

import com.blank.ecommerce.goods.SimpleGoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "分页商品简单对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSimpleGoodsInfo {
    @Schema(description = "分页简单商品信息")
    private List<SimpleGoodsInfo> simpleGoodsInfos;

    @Schema(description = "是否还有更多信息")
    private Boolean hasMore;
}
