package com.st1020.shuttermall.exception;

public class BusinessException extends RuntimeException implements ErrorInfoInterface {
    public final String errorMsg;

    public BusinessException(String msg) {
        errorMsg = msg;
    }

    @Override
    public Integer getErrorCode() {
        return 1;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String getMessage() {
        return getErrorMsg();
    }
}
