package com.blank.ecommerce.controller;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.service.IGoodsService;
import com.blank.ecommerce.vo.PageSimpleGoodsInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
@Tag(name = "商品服务")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @PostMapping("/goods-info")
    List<GoodsInfo> getGoodsInfoByTableId(@RequestBody TableId tableId){
        return goodsService.getGoodsInfoByTableId(tableId);
    }

    @GetMapping("/simple-goods-info-by-page")
    PageSimpleGoodsInfo getPageSimpleGoodsInfoByPage(
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return goodsService.getPageSimpleGoodsInfoByPage(page);
    }

    @PostMapping("/simple-goods-info")
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(@RequestBody TableId  tableId){
        return goodsService.getSimpleGoodsInfoByTableId(tableId);
    }

    @PutMapping("/deduct-goods-inventory")
    Boolean deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories){
        return goodsService.deductGoodsInventory(deductGoodsInventories);
    }
}
