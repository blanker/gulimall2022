package com.blank.ecommerce.dao;

import com.blank.ecommerce.constant.BrandCategoryEnum;
import com.blank.ecommerce.constant.GoodsCategoryEnum;
import com.blank.ecommerce.entity.EcommerceGoods;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcommerceGoodsDao extends PagingAndSortingRepository<EcommerceGoods, Long> {
    Optional<EcommerceGoods> findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
            GoodsCategoryEnum goodsCategory,
            BrandCategoryEnum brandCategory,
            String goodsName
    );
}
