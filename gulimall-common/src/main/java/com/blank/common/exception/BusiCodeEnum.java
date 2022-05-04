package com.blank.common.exception;

public enum BusiCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知错误 "),
    VALID_EXCEPTION(10001, "参数格式校验失败 ");

    private int code;
    private String msg;
    BusiCodeEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
