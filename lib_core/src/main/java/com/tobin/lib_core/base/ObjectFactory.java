package com.tobin.lib_core.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobin.lib_core.BuildConfig;
import com.tobin.lib_core.R;
import com.tobin.lib_core.base.config.CrashManagerConfig;
import com.tobin.lib_core.base.config.GsonConfig;
import com.tobin.lib_core.base.config.OkhttpConfig;
import com.tobin.lib_core.base.config.RetrofitConfig;
import com.tobin.lib_core.base.config.RoomDatabaseConfig;
import com.tobin.lib_core.base.config.SessionManagerConfig;
import com.tobin.lib_core.constant.Constants;
import com.tobin.lib_core.http.cache.RoomCacheInterceptor;
import com.tobin.lib_core.session.MmkvSessionManager;
import com.tobin.lib_core.session.SessionConfig;
import com.tobin.lib_core.session.SessionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

enum ObjectFactory {
    INSTANCE;

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
    private Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
    private Map<String, RoomDatabase.Builder> roomBuilders = new ArrayMap<>();
    private Map<String, RoomDatabase.Builder> roomCacheBuilders;
    private final SessionConfig.Builder sessionBuilder = new SessionConfig.Builder();
    private final CaocConfig.Builder crashBuilder = CaocConfig.Builder.create();

    public Gson getGson(Context context, GlobalConfig globalConfig) {
        GsonConfig gsonConfig = globalConfig.getGsonConfig();
        if (gsonConfig != null) {
            gsonConfig.gson(context, gsonBuilder);
        }
        return gsonBuilder.create();
    }


    public OkHttpClient getOkHttpClient(Context context, GlobalConfig globalConfig) {
        Timber.tag("Tobin").d("ObjectFactory getOkHttpClient");
        okhttpBuilder
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        OkhttpConfig okhttpConfig = globalConfig.getOkhttpConfig();
        if (okhttpConfig != null) {
            okhttpConfig.okhttp(context, okhttpBuilder);
        }

        // 添加动态变更BaseUrl的能力
        RetrofitUrlManager.getInstance().with(okhttpBuilder).build();

        if (BuildConfig.DEBUG) {
            okhttpBuilder.addInterceptor(getLoggingInterceptor());
        }

        // Room 缓存，放在RetrofitUrlManager后面才可以获取替换后的URL
        if (globalConfig.isRoomCache()) {
            okhttpBuilder.addInterceptor(new RoomCacheInterceptor());
        }

        return okhttpBuilder.build();
    }


    /**
     * @param context      Context
     * @param globalConfig GlobalConfig
     * @return {@see https://blog.csdn.net/K_Hello/article/details/81318856}
     */
    public Retrofit getRetrofit(Context context, GlobalConfig globalConfig) {
        String baseUrl = globalConfig.getBaseUrl();
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is null");
        }
        retrofitBuilder.baseUrl(baseUrl);
        OkHttpClient okHttpClient = getOkHttpClient(context, globalConfig);
        retrofitBuilder.client(okHttpClient);
        RetrofitConfig retrofitConfig = globalConfig.getRetrofitConfig();
        if (retrofitConfig != null) {
            retrofitConfig.retrofit(context, retrofitBuilder);
        }
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        return retrofitBuilder.build();
    }

    /**
     * 获取Room数据库实例
     */
    public <DB extends RoomDatabase> DB getRoomDatabase(Context context, Class clazz, GlobalConfig globalConfig) {
        RoomDatabaseConfig roomDatabaseConfig = globalConfig.getRoomDatabaseConfig();
        String roomName = globalConfig.getRoomName();
        if (TextUtils.isEmpty(roomName)) {
            roomName = Constants.NAME_ROOM_DATABASE;
        }
        String keyRoomBuilder = clazz.getSimpleName() + "-" + roomName;
        Timber.tag("Tobin").d("getRoomDatabase keyRoomBuilder: %s", keyRoomBuilder);
        if (roomBuilders.containsKey(keyRoomBuilder)) {
            return (DB) roomBuilders.get(keyRoomBuilder).build();
        }

        RoomDatabase.Builder roomBuilder = Room.databaseBuilder(context, clazz, roomName);
        roomBuilders.put(keyRoomBuilder, roomBuilder);

        if (roomDatabaseConfig != null) {
            roomDatabaseConfig.room(context, roomBuilder);
        }
        return (DB) roomBuilder.build();
    }

    /**
     * 获取缓存数据库
     */
    public <DB extends RoomDatabase> DB getCacheRoomDatabase(Context context, Class clazz) {
        if (roomCacheBuilders == null) {
            roomCacheBuilders = new HashMap<>();
        }
        String keyRoomBuilder = clazz.getSimpleName() + "-" + Constants.ROOM_CACHE_NAME;
        Timber.tag("Tobin").d("getCacheRoomDatabase keyRoomBuilder: %s", keyRoomBuilder);
        if (roomCacheBuilders.containsKey(keyRoomBuilder)) {
            return (DB) roomCacheBuilders.get(keyRoomBuilder).build();
        }
        RoomDatabase.Builder roomBuilder = Room.databaseBuilder(context, clazz, Constants.ROOM_CACHE_NAME)
                .fallbackToDestructiveMigration(); // 升级异常时，重新创建数据库表, 数据丢失;
        if (roomBuilder == null) {
            throw new NullPointerException("create room fail");
        }
        roomCacheBuilders.put(keyRoomBuilder, roomBuilder);
        return (DB) roomBuilder.build();
    }

    public void initSessionManager(Context context, GlobalConfig globalConfig) {
        sessionBuilder.withContext(context)
                //默认使用腾讯的MMKV作为存储用户信息的工具
                .sessionManager(new MmkvSessionManager(context));
        SessionManagerConfig sessionManagerConfig = globalConfig.getSessionManagerConfig();
        if (sessionManagerConfig != null) {
            sessionManagerConfig.session(context, sessionBuilder);
        }
        SessionManager.init(sessionBuilder.build());
    }

    public void initCrashManager(Context context, GlobalConfig globalConfig) {
        crashBuilder
                //程序在后台时，发生崩溃的三种处理方式
                //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
                //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
                //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(false) //false表示对崩溃的拦截阻止。用它来禁用CaocConfig 框架 default: true
                // 这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪,
                // 针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .showErrorDetails(true) //default: true
                // 是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .showRestartButton(true) //default: true
                .logErrorOnRestart(true) //default: true
                //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
                .trackActivities(true) //default: false
                //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.mipmap.error); //default: bug image

        CrashManagerConfig crashManagerConfig = globalConfig.getCrashManagerConfig();
        if (crashManagerConfig != null) {
            crashManagerConfig.crash(context, crashBuilder);
        }

        crashBuilder.apply();
    }

    public HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    public void initAutoSize(GlobalConfig globalConfig) {
        //初始化屏幕适配器
        int designWidth = globalConfig.getDesignWidth();
        int designHeight = globalConfig.getDesignHeight();
        boolean isSupportDP = globalConfig.isSupportDP();
        boolean isSupportSP = globalConfig.isSupportSP();
        Subunits subunits = globalConfig.getSubunits();
        if (designWidth > 0 && designHeight > 0) {
            AutoSizeConfig.getInstance()
                    .setCustomFragment(true)
                    .getUnitsManager()
                    .setDesignWidth(designWidth)
                    .setDesignHeight(designHeight)
                    .setSupportDP(isSupportDP)
                    .setSupportSP(isSupportSP)
                    .setSupportSubunits(subunits);
        }
    }

    public void clear() {
        gsonBuilder = null;
        okhttpBuilder = null;
        retrofitBuilder = null;
        if (roomBuilders != null) {
            roomBuilders.clear();
        }
        roomBuilders = null;
        if (roomCacheBuilders != null) {
            roomCacheBuilders.clear();
        }
        roomCacheBuilders = null;
    }
}
