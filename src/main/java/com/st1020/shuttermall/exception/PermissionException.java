package com.st1020.shuttermall.exception;

public class PermissionException extends RuntimeException implements ErrorInfoInterface {
    public final String errorMsg;

    public PermissionException(String msg) {
        errorMsg = msg;
    }

    @Override
    public Integer getErrorCode() {
        return 403;
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
