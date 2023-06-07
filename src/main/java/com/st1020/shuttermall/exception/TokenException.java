package com.st1020.shuttermall.exception;

public class TokenException extends RuntimeException implements ErrorInfoInterface {
    final String errorMsg;

    public TokenException() {
        errorMsg = "TokenException";
    }

    public TokenException(String msg) {
        errorMsg = msg;
    }

    @Override
    public Integer getErrorCode() {
        return 401;
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
