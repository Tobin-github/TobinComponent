package com.tobin.lib_core.base.config;

import android.content.Context;

import com.tobin.lib_core.session.SessionConfig;

/**
 * description: 用户信息管理里配置
 */
public interface SessionManagerConfig {
    void session(Context context, SessionConfig.Builder builder);
}
