package com.blank.ecommerce.converter;

import com.blank.ecommerce.constant.GoodsCategoryEnum;

import javax.persistence.AttributeConverter;

public class GoodsCategoryConverter implements AttributeConverter<GoodsCategoryEnum, String> {
    @Override
    public String convertToDatabaseColumn(GoodsCategoryEnum goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategoryEnum convertToEntityAttribute(String code) {
        return GoodsCategoryEnum.of(code);
    }
}
