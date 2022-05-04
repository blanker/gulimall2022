package com.blank.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 13:49:04
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listTree();

    void removeMenusByIds(List<Long> asList);

    Long[] getCategoryPath(Long categoryId);
}

