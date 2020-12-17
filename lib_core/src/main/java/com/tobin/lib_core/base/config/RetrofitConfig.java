package com.tobin.lib_core.base.config;

import android.content.Context;

import retrofit2.Retrofit;

/**
 * description: retrofit配置
 */
public interface RetrofitConfig {
    void retrofit(Context context, Retrofit.Builder builder);
}
