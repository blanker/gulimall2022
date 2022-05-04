package com.blank.ecommerce.service.async;

import com.blank.ecommerce.goods.GoodsInfo;

import java.util.List;

public interface IAsyncService {
    void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId);
}
