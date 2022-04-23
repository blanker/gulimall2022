package com.blank.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blank.common.utils.PageUtils;
import com.blank.gulimall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 17:34:08
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

