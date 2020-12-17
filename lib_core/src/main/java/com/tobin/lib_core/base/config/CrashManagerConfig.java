package com.tobin.lib_core.base.config;

import android.content.Context;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * description: 崩溃配置
 */
public interface CrashManagerConfig {
    void crash(Context context, CaocConfig.Builder builder);
}
