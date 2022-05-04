package com.blank.ecommerce.service;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.vo.PageSimpleGoodsInfo;

import java.util.List;

public interface IGoodsService {
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    PageSimpleGoodsInfo getPageSimpleGoodsInfoByPage(int page);

    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
}
