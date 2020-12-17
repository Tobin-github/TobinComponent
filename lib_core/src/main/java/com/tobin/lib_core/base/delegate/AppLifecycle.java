package com.tobin.lib_core.base.delegate;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

/**
 * 用于代理 {@link Application} 的生命周期
 *
 * @see AppDelegate
 */
public interface AppLifecycle {
    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);

    void onConfigurationChanged(@NonNull Configuration newConfig);
}
