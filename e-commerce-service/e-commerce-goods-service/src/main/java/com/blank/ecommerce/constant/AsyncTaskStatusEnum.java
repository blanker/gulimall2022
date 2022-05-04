package com.blank.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AsyncTaskStatusEnum {

    STARTED(0 ,"已经启动"),
    RUNNING(1 ,"正在运行"),
    SUCCESS(2 ,"执行成功"),
    FAILED(3 ,"执行失败"),
    ;

    private final int state;
    private final String stateInfo;

}
