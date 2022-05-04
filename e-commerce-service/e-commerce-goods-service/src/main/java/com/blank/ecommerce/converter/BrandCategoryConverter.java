package com.blank.ecommerce.converter;

import com.blank.ecommerce.constant.BrandCategoryEnum;

import javax.persistence.AttributeConverter;

public class BrandCategoryConverter implements AttributeConverter<BrandCategoryEnum, String> {
    @Override
    public String convertToDatabaseColumn(BrandCategoryEnum brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategoryEnum convertToEntityAttribute(String code) {
        return BrandCategoryEnum.of(code);
    }
}
