package com.blank.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blank.common.utils.PageUtils;
import com.blank.common.utils.Query;

import com.blank.gulimall.product.dao.BrandDao;
import com.blank.gulimall.product.entity.BrandEntity;
import com.blank.gulimall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        final String key = (String) params.get("key");

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                Wrappers.lambdaQuery(BrandEntity.class)
                        .and(StringUtils.isNotBlank(key), consumer -> {
                            consumer.eq(BrandEntity::getBrandId, key)
                                    .or()
                                    .like(BrandEntity::getName, key);
                        })
        );

        return new PageUtils(page);
    }

}