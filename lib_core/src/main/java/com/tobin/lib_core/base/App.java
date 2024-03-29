package com.tobin.lib_core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;

import com.tobin.lib_core.BuildConfig;
import com.tobin.lib_core.base.delegate.AppDelegate;
import com.tobin.lib_core.base.delegate.AppLifecycle;
import com.tobin.lib_core.base.delegate.GlobalModule;
import com.tobin.lib_core.base.delegate.MetaValue;
import com.tobin.lib_core.log.CrashReportingTree;
import com.tobin.lib_core.utils.KVUtils;
import com.tobin.lib_core.utils.ManifestParser;

import java.util.List;

import timber.log.Timber;

public class App extends Application implements ViewModelStoreOwner {
    private AppLifecycle appLifecycle;
    private static Application instance;
    private static GlobalConfig globalConfig;
    private static GlobalConfig.Builder globalBuilder;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //初始化分包插件
        MultiDex.install(base);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        Timber.tag("Tobin").i("App attachBaseContext");

        if (appLifecycle == null) {
            appLifecycle = new AppDelegate(base);
        }

        appLifecycle.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.tag("Tobin").i("App onCreate");
        mAppViewModelStore = new ViewModelStore();

        // 初始化全局配置
        initGlobalConfig();

        // 初始化屏幕适配器
        ObjectFactory.INSTANCE.initAutoSize(getGlobalConfig());

        // 初始化KVUtil
        KVUtils.init(this);

        // 用户信息管理器
        ObjectFactory.INSTANCE.initSessionManager(this, getGlobalConfig());

        // 崩溃拦截配置
        ObjectFactory.INSTANCE.initCrashManager(this, getGlobalConfig());

        appLifecycle.onCreate(instance);

    }

    private void initGlobalConfig() {
        // 先初始化全局配置，然后进行生命周期的分发
        List<GlobalModule> globalModules = new ManifestParser<GlobalModule>(instance, MetaValue.GLOBAL_CONFIG).parse();
        if (globalModules == null) {
            throw new IllegalArgumentException("Please config global");
        }
        if (globalModules.size() > 1) {
            throw new IllegalArgumentException("Only one GlobalConfig initialization is allowed");
        }
        GlobalModule globalModule = globalModules.get(0);
        globalBuilder = GlobalConfig.builder();
        globalModule.applyOptions(instance, globalBuilder);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Timber.tag("Tobin").i("App onTerminate: ");
        appLifecycle.onTerminate(instance);
        ObjectFactory.INSTANCE.clear();
        appLifecycle = null;
        instance = null;
        globalConfig = null;
        globalBuilder = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        appLifecycle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
        Timber.tag("Tobin").i("onConfigurationChanged:");
    }

    /**
     * 获取Application的实例，一般在工具类或者生命周期较早的初始化中使用
     */
    public static Application getApp() {
        return instance;
    }

    public static GlobalConfig getGlobalConfig() {
        if (globalConfig == null) {
            globalConfig = globalBuilder.build();
        }
        return globalConfig;
    }


    private ViewModelStore mAppViewModelStore;
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
