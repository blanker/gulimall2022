package com.blank.gulimall.order.dao;

import com.blank.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author blank
 * @email blank@gmail.com
 * @date 2022-04-23 17:34:08
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
