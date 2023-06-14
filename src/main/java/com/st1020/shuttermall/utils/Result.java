package com.st1020.shuttermall.utils;

import com.st1020.shuttermall.exception.ErrorInfoInterface;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(T data) {
        setCode(0);
        setData(data);
    }

    public Result(Integer code, T data) {
        setCode(code);
        setData(data);
    }

    public static Result<Object> error(ErrorInfoInterface errorInfo) {
        Result<Object> res = new Result<>();
        res.setCode(errorInfo.getErrorCode());
        res.setMsg(errorInfo.getErrorMsg());
        return res;
    }

    public static Result<Object> error(Integer code, String msg) {
        Result<Object> res = new Result<>();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
