package com.membership.exception;

import com.membership.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(OperationFailedException.class)
    public Result handleOperationFailedException(OperationFailedException e) {
        log.error("操作失败" + e.getMessage());
        return Result.error("操作失败" + e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result handleIOException(IOException e) {
        log.error("IO异常", e);//描述
        return Result.error("文件处理失败: " + e.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    public Result handleLoginFailedException(LoginFailedException e) {
        log.error("登录失败，" + e.getMessage());
        return Result.error("登录失败：" + e.getMessage());
    }


}
