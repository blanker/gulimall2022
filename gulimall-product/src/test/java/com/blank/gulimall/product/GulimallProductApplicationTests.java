package com.blank.gulimall.product;

import com.blank.gulimall.product.entity.BrandEntity;
import com.blank.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class GulimallProductApplicationTests {
    @Autowired
    private BrandService brandService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Huawei");
        brandService.save(brandEntity);
    }


}
