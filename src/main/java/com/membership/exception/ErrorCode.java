package com.membership.exception;

public enum ErrorCode {
    OPERATION_FAILED(4001001, "操作失败"),
    LOGIN_FAILED(4001002, "登录失败，用户名或密码错误");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
