package com.tobin.lib_core.http.model;

import com.google.gson.annotations.SerializedName;

/**
 * 默认HttpResult,如果格式和此处有较大差异，可implements BaseModel进行扩展
 */
public class HttpResult<T> implements BaseModel<T> {

    @SerializedName("tag")
    private boolean tag;

    @SerializedName(value = "code", alternate = {"errorCode"})
    private int code;

    @SerializedName(value = "message", alternate = {"status", "msg","errorMsg"})
    private String message;

    @Override
    public boolean isSuccess() {
        return tag;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    //用来模仿Data
    @SerializedName(value = "data", alternate = {"subjects", "result", "error"})
    private T data;

    @Override
    public T getData() {
        return data;
    }

}
