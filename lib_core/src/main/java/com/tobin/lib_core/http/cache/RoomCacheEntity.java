package com.tobin.lib_core.http.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import okhttp3.Headers;
import timber.log.Timber;

@Entity(tableName = "RoomCache")
public class RoomCacheEntity implements Serializable {
    @Ignore
    private static final long serialVersionUID = -4337711009801627866L;
    @Ignore
    public static final long CACHE_NEVER_EXPIRE = -1;        //缓存永不过期

    //表中的字段
    @Ignore
    public static final String KEY = "key";
    @Ignore
    public static final String LOCAL_EXPIRE = "localExpire";
    @Ignore
    public static final String HEAD = "head";
    @Ignore
    public static final String DATA = "data";

    @PrimaryKey
    @NonNull
    private String key;                 // 缓存key
    private long localExpire;           // 缓存过期时间
    private String protocol;
    @Nullable
    private Headers responseHeaders;   // 缓存的响应头
    @Nullable
    private String data;                        // 缓存的实体数据
    private boolean isExpire;   //缓存是否过期该变量不必保存到数据库，程序运行起来后会动态计算

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Headers responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getLocalExpire() {
        return localExpire;
    }

    public void setLocalExpire(long localExpire) {
        this.localExpire = localExpire;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isExpire() {
        return isExpire;
    }

    public void setExpire(boolean expire) {
        isExpire = expire;
    }

    /**
     * @param cacheTime 允许的缓存时间
     * @param baseTime  基准时间,小于当前时间视为过期
     * @return 是否过期
     */
    @Ignore
    public boolean checkExpire(String cacheMode, long cacheTime, long baseTime) {
        //304的默认缓存模式,设置缓存时间无效,需要依靠服务端的响应头控制
        if (cacheMode.equals(CacheMode.DEFAULT)) return getLocalExpire() < baseTime;
        if (cacheTime == CACHE_NEVER_EXPIRE) return false;
        Timber.tag("Tobin").d("LocalExpire: " + getLocalExpire() + "\ncacheTime: "
                + cacheTime + "\nbaseTime: " + baseTime);
        return getLocalExpire() + cacheTime < baseTime;
    }

    @Override
    public String toString() {
        return "RoomCacheEntity{" +
                "key='" + key + '\'' +
                ", localExpire=" + localExpire +
                ", protocol='" + protocol + '\'' +
                ", data='" + data + '\'' +
                ", isExpire=" + isExpire +
                ", responseHeaders=" + responseHeaders +
                '}';
    }
}
