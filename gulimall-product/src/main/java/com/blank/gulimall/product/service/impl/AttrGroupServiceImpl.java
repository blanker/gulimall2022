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

import com.blank.gulimall.product.dao.AttrGroupDao;
import com.blank.gulimall.product.entity.AttrGroupEntity;
import com.blank.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        final String key = (String) params.get("key");
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                Wrappers.lambdaQuery(AttrGroupEntity.class)
                        .eq(categoryId != 0, AttrGroupEntity::getCatelogId, categoryId)
                        .and(StringUtils.isNotBlank(key), consumer -> {
                            consumer.eq(AttrGroupEntity::getAttrGroupId, key)
                                    .or()
                                    .like(AttrGroupEntity::getAttrGroupName, key);
                        })
                );

        return new PageUtils(page);
    }


}