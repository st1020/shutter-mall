package com.st1020.shuttermall.exception;

public class PermissionException extends RuntimeException implements ErrorInfoInterface {
    private final String errorMsg;

    public PermissionException() {
        errorMsg = "PermissionException";
    }

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
