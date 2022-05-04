package com.blank.ecommerce.constant;

import com.alibaba.nacos.common.utils.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GoodsCategoryEnum {

    DIAN_QI("10001", "电器"),
    JIA_JU("10002", "家居"),
    FU_SHI("10003", "服饰"),
    SHI_PIN("10004", "食品"),
    TU_SHU("10005", "图书"),
    ;

    private final String code;
    private final String description;

    public static GoodsCategoryEnum of(String code){
        Objects.requireNonNull(code);

        return Arrays.stream(GoodsCategoryEnum.values())
                .filter(bean -> bean.getCode().equals(code))
                .findAny()
                .orElseThrow(
                        ()->new IllegalArgumentException(code + " not exists")
                );
    }
}
