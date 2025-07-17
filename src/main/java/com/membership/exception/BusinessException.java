package com.membership.exception;

public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode; // 可以定义枚举错误码

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
