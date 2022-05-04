package com.blank.ecommerce.constant;

import com.alibaba.nacos.common.utils.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GoodsStatusEnum {

    ONLINE(101, "上线"),
    OFFLINE(102, "下线"),
    STOCK_OUT(103, "缺货"),
    ;

    private Integer status;
    private String description;

    public static GoodsStatusEnum of(Integer status){
        Objects.requireNonNull(status);

        return Arrays.stream(GoodsStatusEnum.values())
                .filter(bean -> bean.getStatus().equals(status))
                .findAny()
                .orElseThrow(
                        ()->new IllegalArgumentException(status + " not exists")
                );
    }
}
