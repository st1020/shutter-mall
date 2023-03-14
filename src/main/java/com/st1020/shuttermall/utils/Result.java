package com.st1020.shuttermall.utils;

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

    public Result(String msg) {
        setCode(-1);
        setMsg(msg);
    }

    public Result(Integer code, T data) {
        setCode(code);
        setData(data);
    }

    public Result(Integer code, String msg) {
        setCode(code);
        setMsg(msg);
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
