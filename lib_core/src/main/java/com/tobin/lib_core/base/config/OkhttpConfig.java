package com.tobin.lib_core.base.config;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * description: okhttp配置
 */
public interface OkhttpConfig {
    void okhttp(Context context, OkHttpClient.Builder builder);
}
