package com.st1020.shuttermall.exception;

import com.st1020.shuttermall.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = TokenException.class)
    @ResponseBody
    public Result<Object> tokenExceptionHandler(HttpServletRequest req, TokenException e) {
        return Result.error(e);
    }

    @ExceptionHandler(value = PermissionException.class)
    @ResponseBody
    public Result<Object> permissionExceptionHandler(HttpServletRequest req, PermissionException e) {
        return Result.error(e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> exceptionHandler(HttpServletRequest req, Exception e) {
        System.out.println(e.getMessage());
        return Result.error(500, e.getMessage());
    }
}
