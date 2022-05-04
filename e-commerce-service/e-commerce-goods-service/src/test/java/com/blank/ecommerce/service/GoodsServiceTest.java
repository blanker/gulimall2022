package com.blank.ecommerce.service;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.vo.PageSimpleGoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class GoodsServiceTest {
    @Autowired
    private IGoodsService goodsService;

    //    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);
    @Test
    public void testGetGoodsInfoByTableId() {
        final List<GoodsInfo> goodsInfoByTableId = goodsService.getGoodsInfoByTableId(
                new TableId(Arrays.asList(1L, 2L, 3L)
                        .stream().map(TableId.Id::new)
                        .collect(Collectors.toList()))
        );
        log.info("goodsInfoByTableId: [{}]", goodsInfoByTableId);
    }

//    PageSimpleGoodsInfo getPageSimpleGoodsInfoByPage(int page);
    @Test
    public void testGetPageSimpleGoodsInfoByPage(){
        final PageSimpleGoodsInfo pageSimpleGoodsInfoByPage = goodsService.getPageSimpleGoodsInfoByPage(0);
        log.info("pageSimpleGoodsInfoByPage: [{}]", pageSimpleGoodsInfoByPage);
    }

//    List<SimpleGoodsInfo> getSimpleGoodsInfoByByTableId(TableId tableId);
    @Test
    public void testGetSimpleGoodsInfoByTableId() {
        final List<SimpleGoodsInfo> simpleGoodsInfoByTableId = goodsService.getSimpleGoodsInfoByTableId(
                new TableId(Arrays.asList(1L, 2L, 3L)
                        .stream().map(TableId.Id::new)
                        .collect(Collectors.toList()))
        );
        log.info("simpleGoodsInfoByTableId: [{}]", simpleGoodsInfoByTableId);
    }

    //    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
    @Test
    public void testDeductGoodsInventory() {
        final Boolean result = goodsService.deductGoodsInventory(Arrays.asList(
                new DeductGoodsInventory(1L, 100),
                new DeductGoodsInventory(2L, 200)
        ));
        log.info("testDeductGoodsInventory result: [{}]", result);
    }

}
