package com.blank.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 13:49:04
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

