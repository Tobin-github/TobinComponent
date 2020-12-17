package com.tobin.lib_core.http.model;

public interface BaseModel<T> {
    boolean isSuccess();

    int getCode();

    String getMessage();

    T getData();
}
