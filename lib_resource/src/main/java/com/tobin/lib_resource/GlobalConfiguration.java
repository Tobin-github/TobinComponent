package com.tobin.lib_resource;

import android.content.Context;

import androidx.room.RoomDatabase;

import com.tobin.lib_core.base.GlobalConfig;
import com.tobin.lib_core.base.config.CrashManagerConfig;
import com.tobin.lib_core.base.config.OkhttpConfig;
import com.tobin.lib_core.base.config.RetrofitConfig;
import com.tobin.lib_core.base.config.RoomDatabaseConfig;
import com.tobin.lib_core.base.config.SessionManagerConfig;
import com.tobin.lib_core.base.delegate.GlobalModule;
import com.tobin.lib_core.http.cache.CacheMode;
import com.tobin.lib_core.http.cache.RoomCacheInterceptor;
import com.tobin.lib_core.session.SessionConfig;
import com.tobin.lib_core.session.SessionToken;
import com.tobin.lib_core.session.SessionUserInfo;
import com.tobin.lib_resource.utils.DeviceUtils;
import com.tobin.lib_resource.utils.ProcessUtils;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class GlobalConfiguration implements GlobalModule {
    private static final String TAG = "GlobalConfiguration";

    @Override
    public void applyOptions(Context context, GlobalConfig.Builder builder) {
        Timber.i("GlobalConfiguration---->applyOptions");
        builder
                // 全局BaseUrl
                .baseurl(BuildConfig.SERVER_ADDRESS)
                // Room数据库的名字
                .roomName("Tobin")
                // 设计图的宽 单位：px
                .designWidth(720)
                // 设计图的高 单位：px
                .designHeight(1280)
                // 设置对副单位的支持
                .autoSize(false, false, Subunits.PT)
                //配置是否Room数据库进行网络请求的缓存
                .roomCache(true, CacheMode.REQUEST_FAILED_READ_CACHE, 60*60*24*30)
                // OkHttpClient的拓展配置
                .okhttpConfiguration(new OkhttpConfig() {
                    @Override
                    public void okhttp(Context context, OkHttpClient.Builder builder) {
//                        builder.addInterceptor(new RoomCacheInterceptor())
//                                //添加统一请求头
//                                .addInterceptor(new Interceptor() {
//                                    @Override
//                                    public Response intercept(Chain chain) throws IOException {
//                                        Request request = chain.request()
//                                                .newBuilder()
//                                                .addHeader("equipmentId", DeviceUtils.getIMEI())
//                                                .addHeader("serverId", "1")
//                                                .build();
//                                        return chain.proceed(request);
//                                    }
//                                });

                        if (context.getPackageName().equals(ProcessUtils.getCurProcessName(context))) {
//                            builder.addInterceptor(Pandora.get().getInterceptor());
                        }
                    }
                })
                // 用户信息全局管理配置
                .sessionManagerConfiguration(new SessionManagerConfig() {
                    @Override
                    public void session(Context context, SessionConfig.Builder builder) {
                        // SessionUserInfo 和 SessionToken 是默认的实体类
                        builder.userClass(SessionUserInfo.class).tokenClass(SessionToken.class);
                    }
                })
                // Room数据库配置
                .roomDatabaseConfiguration(new RoomDatabaseConfig() {
                    @Override
                    public void room(Context context, RoomDatabase.Builder builder) {
                        Timber.i("roomDatabaseConfiguration");
                    }
                })
                // Retrofit拓展配置
                .retrofitConfiguration(new RetrofitConfig() {
                    @Override
                    public void retrofit(Context context, Retrofit.Builder builder) {
                        Timber.i("retrofitConfiguration");
                        RetrofitUrlManager.getInstance().putDomain("wxjdcloud", BuildConfig.API_WX_JDCLOUD);
                    }
                })
                // 崩溃信息拦截器拓展配置
                .crashManagerConfiguration(new CrashManagerConfig() {
                    @Override
                    public void crash(Context context, CaocConfig.Builder builder) {
                        // 关闭崩溃全局监听
                        builder.enabled(BuildConfig.DEBUG);
                    }
                });

    }
}
