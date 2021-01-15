package com.tobin.lib_core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.RoomDatabase;

import com.google.gson.Gson;
import com.tobin.lib_core.base.delegate.AppLifecycle;
import com.tobin.lib_core.session.SessionManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import timber.log.Timber;

public class Box implements AppLifecycle {
    private static Application mApplication;
    private static Gson gson;
    private static Retrofit retrofit;
    private static Handler handler;
    private static OkHttpClient okHttpClient;

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        mApplication = application;
        Timber.i("onCreate");
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        mApplication = null;
        gson = null;
        retrofit = null;
        Timber.i("onTerminate");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
    }


    public static Application getApp() {
        return mApplication;
    }

    /**
     * Gson
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = ObjectFactory.INSTANCE.getGson(mApplication, App.getGlobalConfig());
        }
        return gson;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = ObjectFactory.INSTANCE.getOkHttpClient(mApplication, App.getGlobalConfig());
        }
        return okHttpClient;
    }

    /**
     * api service of Retrofit
     */
    public static <T> T getRetrofit(Class<T> serviceClazz) {
        if (retrofit == null) {
            retrofit = ObjectFactory.INSTANCE.getRetrofit(mApplication, App.getGlobalConfig());
        }
        return retrofit.create(serviceClazz);
    }

    /**
     * 数据库Room
     */
    public static <DB extends RoomDatabase> DB getRoomDataBase(Class<? extends RoomDatabase> databaseClazz) {
        return ObjectFactory.INSTANCE.getRoomDatabase(mApplication, databaseClazz, App.getGlobalConfig());
    }

    /**
     * 获取缓存数据库
     */
    public static <DB extends RoomDatabase> DB getCacheRoomDataBase(Class<? extends RoomDatabase> databaseClazz) {
        return ObjectFactory.INSTANCE.getCacheRoomDatabase(mApplication, databaseClazz);
    }

    /**
     * 用户信息管理器
     */
    public static SessionManager getSessionManager() {
        return SessionManager.get();
    }

    /**
     * 为了解决在fragment中使用{@link Fragment#getString(int)}
     * 偶尔会报java.lang.IllegalStateException-->Fragment xxxxx{xxx} not attached to a context的错误
     *
     */
    public static String getString(@StringRes int id) {
        return getApp().getResources().getString(id);
    }

    /**
     * 为了解决在fragment中使用{@link Fragment#getContext()} getContext().getColor(int color)
     * 偶尔会报java.lang.IllegalStateException-->Fragment xxxxx{xxx} not attached to a context的错误
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getApp(), id);
    }

    /**
     * 提供全局的handler
     */
    public static Handler getHandler(Looper looper) {
        if (handler == null) {
            handler = new Handler(looper);
        }
        return handler;
    }
}
