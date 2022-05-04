package com.blank.ecommerce.constant;

import com.alibaba.nacos.common.utils.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BrandCategoryEnum {

    BRAND_A("20001", "品牌A"),
    BRAND_B("20002", "品牌B"),
    BRAND_C("20003", "品牌C"),
    BRAND_D("20004", "品牌D"),
    ;

    private final String code;
    private final String description;

    public static BrandCategoryEnum of(String code){
        Objects.requireNonNull(code);

        return Arrays.stream(BrandCategoryEnum.values())
                .filter(bean -> bean.getCode().equals(code))
                .findAny()
                .orElseThrow(
                        ()->new IllegalArgumentException(code + " not exists")
                );
    }
}
