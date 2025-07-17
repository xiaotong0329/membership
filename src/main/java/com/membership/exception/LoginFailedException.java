package com.membership.exception;

public class LoginFailedException extends BusinessException {
    public LoginFailedException(String message) {
       super(ErrorCode.LOGIN_FAILED, message);
    }
}
