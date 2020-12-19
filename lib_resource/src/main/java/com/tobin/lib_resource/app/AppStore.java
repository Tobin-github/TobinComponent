package com.tobin.lib_resource.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.core.LoadSir;
import com.tobin.lib_core.base.delegate.AppLifecycle;
import com.tobin.lib_core.utils.AppUtils;
import com.tobin.lib_resource.BuildConfig;
import com.tobin.lib_resource.loadsir.AnimateCallback;
import com.tobin.lib_resource.loadsir.CustomCallback;
import com.tobin.lib_resource.loadsir.ErrorCallback;
import com.tobin.lib_resource.loadsir.LottieEmptyCallback;
import com.tobin.lib_resource.loadsir.LottieLoadingCallback;
import com.tobin.lib_resource.loadsir.TimeoutCallback;
import com.tencent.bugly.crashreport.CrashReport;

public class AppStore implements AppLifecycle {
    private static final String TAG = "AppStore";
    private static final String BUGLY_APPID = "";

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        //初始化路由框架
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);

        //初始化 Bugly
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(application);
        userStrategy.setAppChannel(AppUtils.getAppInfo().getPackageName());
        userStrategy.setAppVersion(AppUtils.getAppInfo().getVersionName());
        CrashReport.initCrashReport(application, BUGLY_APPID, BuildConfig.DEBUG, userStrategy);

        //初始化状态页面控件LoadSir
        LoadSir.beginBuilder()
                .addCallback(new AnimateCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new LottieEmptyCallback())
                .addCallback(new LottieLoadingCallback())
                .addCallback(new CustomCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LottieLoadingCallback.class)
                .commit();
    }

    @Override
    public void onTerminate(@NonNull Application application) {
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

    }
}
