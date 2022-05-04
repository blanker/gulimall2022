package com.blank.ecommerce.converter;

import com.blank.ecommerce.constant.GoodsStatusEnum;

import javax.persistence.AttributeConverter;

public class GoodsStatusConverter implements AttributeConverter<GoodsStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GoodsStatusEnum goodsStatusEnum) {
        return goodsStatusEnum.getStatus();
    }

    @Override
    public GoodsStatusEnum convertToEntityAttribute(Integer status) {
        return GoodsStatusEnum.of(status);
    }
}
