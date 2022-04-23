package com.blank.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 13:49:04
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

