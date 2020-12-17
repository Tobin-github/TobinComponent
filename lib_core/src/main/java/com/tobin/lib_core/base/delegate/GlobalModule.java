package com.tobin.lib_core.base.delegate;

import android.content.Context;

import com.tobin.lib_core.base.GlobalConfig;

/**
 * description: 全局配置
 */
public interface GlobalModule {
    /**
     * 使用{@link com.tobin.lib_core.base.GlobalConfig.Builder}给框架配置一些配置参数
     * meta中value{@link  com.tobin.lib_core.base.delegate.MetaValue#GLOBAL_CONFIG}
     */
    void applyOptions(Context context, GlobalConfig.Builder builder);
}
