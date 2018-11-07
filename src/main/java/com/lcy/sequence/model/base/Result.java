package com.lcy.sequence.model.base;

import java.io.Serializable;

/**
 * 返回结果
 * Created by DELL on 2018/6/12.
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1802638588801407401L;
    private String code;//返回码
    private String msg;//返回值
    private T data;//返回数据

    public Result() {
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

