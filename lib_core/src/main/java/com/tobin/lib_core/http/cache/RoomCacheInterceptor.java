package com.tobin.lib_core.http.cache;

import android.text.TextUtils;

import com.tobin.lib_core.base.App;
import com.tobin.lib_core.base.Box;
import com.tobin.lib_core.base.GlobalConfig;
import com.tobin.lib_core.utils.NetworkUtils;
import com.tobin.lib_core.utils.ThreadUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * 在此处实现数据库的缓存
 */
public class RoomCacheInterceptor implements Interceptor {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=UTF-8");

    @Override
    public Response intercept(final Chain chain) throws IOException {

        final Request request = chain.request();
        GlobalConfig globalConfig = App.getGlobalConfig();
        String roomHeader = request.header("Room-Cache");
        long roomCacheTime = TextUtils.isEmpty(roomHeader) ? globalConfig.getGlobalCacheSecond() * 1000 : Integer.parseInt(roomHeader) * 1000;
        String okhttpHeader = request.header("OkHttp-Cache");
        int okhttpCacheTime = TextUtils.isEmpty(okhttpHeader) ? globalConfig.getGlobalCacheSecond() : Integer.parseInt(okhttpHeader);
        String cacheMode = request.header("Cache-Mode");
        String globalCacheMode = TextUtils.isEmpty(cacheMode) ? globalConfig.getCacheMode() : cacheMode;
        boolean isRoomCache = globalConfig.isRoomCache();
        boolean isOkhttpCache = globalCacheMode.equals(CacheMode.DEFAULT) || !isRoomCache;
        if (NetworkUtils.isNetworkAvailable()) {
            //OkHttp缓存
            if (isOkhttpCache) {
                //DEFAULT模式
                return readOkhttpCache(chain.proceed(request), okhttpCacheTime);
            }
            //既非okhttp缓存，也不是room缓存
            if (!isRoomCache) {
                return chain.proceed(request);
            }
            //room缓存
            if (globalCacheMode.equals(CacheMode.REQUEST_FAILED_READ_CACHE)) {
                //REQUEST_FAILED_READ_CACHE模式
                Response response = chain.proceed(request);
                if (response.code() != 200) {
                    return readRoomCacheWithRequestFailedReadCache(true, response, roomCacheTime);
                } else {
                    //请求成功写入数据库
                    return writeRoomCache(response, true);
                }
            }

            if (globalCacheMode.equals(CacheMode.IF_NONE_CACHE_REQUEST)) {
                //IF_NONE_CACHE_REQUEST模式
                return readRoomCacheWithIfNoneCacheRequest(true, chain, request, roomCacheTime);
            }

            if (globalCacheMode.equals(CacheMode.FIRST_CACHE_THEN_REQUEST)) {
                //FIRST_CACHE_THEN_REQUEST模式
                ThreadUtils.postOnBackgroundThread(new Runnable() {
                    @Override
                    public void run() {
                        Response response = null;
                        try {
                            response = chain.proceed(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                writeRoomCache(response, false);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                Response roomResponse = readRoomCacheWithFirstCacheThenRequest(request, roomCacheTime);
                return roomResponse == null ?
                        get400Response(request, null, CacheMode.FIRST_CACHE_THEN_REQUEST, "http/1.1") : roomResponse;
            }
        } else {
            //无网模式
            if (isOkhttpCache) {
                return readOkhttpCacheOnly(chain, request);
            }
            if (!isRoomCache) {
                return chain.proceed(request);
            }
            //room缓存
            if (globalCacheMode.equals(CacheMode.REQUEST_FAILED_READ_CACHE)) {
                //REQUEST_FAILED_READ_CACHE模式
                Response response = readRoomCacheWithRequestFailedReadCacheNotNet(request, roomCacheTime);
                return response == null
                        ? get400Response(request, null, CacheMode.REQUEST_FAILED_READ_CACHE, "http/1/1")
                        : response;
            }
            if (globalCacheMode.equals(CacheMode.IF_NONE_CACHE_REQUEST)) {
                //IF_NONE_CACHE_REQUEST模式
                return readRoomCacheWithIfNoneCacheRequest(false, chain, request, roomCacheTime);
            }

            if (globalCacheMode.equals(CacheMode.FIRST_CACHE_THEN_REQUEST)) {
                //FIRST_CACHE_THEN_REQUEST模式
                return readRoomCacheWithFirstCacheThenRequestNotNet(request, roomCacheTime);
            }
        }
        return chain.proceed(request);
    }

    private Response readOkhttpCache(Response response, int maxAge) {
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();
    }

    private Response readOkhttpCacheOnly(Chain chain, Request request) throws IOException {
        //读取缓存信息
        request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        Response response = chain.proceed(request);
        Timber.i("on cache data:" + response.cacheResponse());
        //set cache times is 3 days
        int maxStale = 60 * 60 * 24 * 3;
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                .build();
    }

    private Response readRoomCacheWithRequestFailedReadCache(boolean isNetOk, Response response, long time) throws IOException {
        Request request = response.request();
        String key = request.url().url().toString() + ">" + request.method();
        Timber.i("RoomCache-Key(get):" + key);
        RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
        RoomCacheEntity roomCacheEntity = roomCacheDB.roomCacheDao().queryByKey(key);
        roomCacheDB.close();
        if (roomCacheEntity == null)
            return response;
        boolean isExpire = roomCacheEntity.checkExpire(CacheMode.REQUEST_FAILED_READ_CACHE, time, System.currentTimeMillis());
        Timber.i(key + ">>>>>isExpire(" + isExpire + ")");
        if (isExpire) {
            if (isNetOk)
                return response;
            return get400Response(request, roomCacheEntity.getResponseHeaders(),
                    CacheMode.REQUEST_FAILED_READ_CACHE, roomCacheEntity.getProtocol());
        } else {
            return get200RoomResponse(request, roomCacheEntity, CacheMode.REQUEST_FAILED_READ_CACHE);
        }
    }

    private Response readRoomCacheWithRequestFailedReadCacheNotNet(Request request, long time) throws IOException {
        String key = request.url().url().toString() + ">" + request.method();
        Timber.i("RoomCache-Key(get):" + key);
        RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
        RoomCacheEntity roomCacheEntity = roomCacheDB.roomCacheDao().queryByKey(key);
        roomCacheDB.close();
        if (roomCacheEntity == null)
            return null;
        boolean isExpire = roomCacheEntity.checkExpire(CacheMode.REQUEST_FAILED_READ_CACHE, time, System.currentTimeMillis());
        Timber.i(key + ">>>>>isExpire(" + isExpire + ")");
        if (isExpire) {
            return null;
        } else {
            return get200RoomResponse(request, roomCacheEntity, CacheMode.REQUEST_FAILED_READ_CACHE);
        }
    }

    private Response readRoomCacheWithIfNoneCacheRequest(boolean isNetOk, Chain chain, Request request, long time) throws IOException {
        String key = request.url().url().toString() + ">" + request.method();
        Timber.i("RoomCache-Key(get):" + key);
        RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
        RoomCacheEntity roomCacheEntity = roomCacheDB.roomCacheDao().queryByKey(key);
        roomCacheDB.close();
        if (roomCacheEntity == null)
            return writeRoomCache(chain.proceed(request), true);
        boolean isExpire = roomCacheEntity.checkExpire(CacheMode.IF_NONE_CACHE_REQUEST, time, System.currentTimeMillis());
        Timber.i(key + ">>>>>isExpire(" + isExpire + ")");
        if (isExpire) {
            if (isNetOk)
                return writeRoomCache(chain.proceed(request), true);
            return get400Response(request, roomCacheEntity.getResponseHeaders(),
                    CacheMode.IF_NONE_CACHE_REQUEST, roomCacheEntity.getProtocol());
        } else {
            return get200RoomResponse(request, roomCacheEntity, CacheMode.IF_NONE_CACHE_REQUEST);
        }
    }

    private Response readRoomCacheWithFirstCacheThenRequest(Request request, long time) throws IOException {
        String key = request.url().url().toString() + ">" + request.method();
        Timber.i("RoomCache-Key(get):" + key);
        RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
        RoomCacheEntity roomCacheEntity = roomCacheDB.roomCacheDao().queryByKey(key);
        roomCacheDB.close();
        if (roomCacheEntity == null)
            return null;
        boolean isExpire = roomCacheEntity.checkExpire(CacheMode.FIRST_CACHE_THEN_REQUEST, time, System.currentTimeMillis());
        Timber.i(key + ">>>>>isExpire(" + isExpire + ")");
        if (isExpire) {
            return null;
        } else {
            return get200RoomResponse(request, roomCacheEntity, CacheMode.FIRST_CACHE_THEN_REQUEST);
        }
    }

    private Response readRoomCacheWithFirstCacheThenRequestNotNet(Request request, long time) throws IOException {
        String key = request.url().url().toString() + ">" + request.method();
        Timber.i("RoomCache-Key(get):" + key);
        RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
        RoomCacheEntity roomCacheEntity = roomCacheDB.roomCacheDao().queryByKey(key);
        roomCacheDB.close();
        if (roomCacheEntity == null)
            return get400Response(request, null,
                    CacheMode.FIRST_CACHE_THEN_REQUEST, roomCacheEntity.getProtocol());
        boolean isExpire = roomCacheEntity.checkExpire(CacheMode.FIRST_CACHE_THEN_REQUEST, time, System.currentTimeMillis());
        Timber.i(key + ">>>>>isExpire(" + isExpire + ")");
        if (isExpire) {
            return get400Response(request, roomCacheEntity.getResponseHeaders(),
                    CacheMode.FIRST_CACHE_THEN_REQUEST, roomCacheEntity.getProtocol());
        } else {
            return get200RoomResponse(request, roomCacheEntity, CacheMode.FIRST_CACHE_THEN_REQUEST);
        }
    }

    private Response writeRoomCache(final Response response, boolean isBuildNewResponse) throws IOException {
        if (response == null) return response;
        final String content = response.body().string();
        //构造缓存实体并写入数据库
        ThreadUtils.postOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                String key = response.request().url().url().toString() + ">" + response.request().method();
                Timber.i("RoomCache-Key(put):" + key);
                String protocol = response.protocol().toString();
                RoomCacheDB roomCacheDB = Box.getCacheRoomDataBase(RoomCacheDB.class);
                RoomCacheEntity roomCacheEntity = new RoomCacheEntity();
                roomCacheEntity.setLocalExpire(System.currentTimeMillis());
                roomCacheEntity.setExpire(false);
                roomCacheEntity.setKey(key);
                roomCacheEntity.setData(content);
                roomCacheEntity.setResponseHeaders(response.headers());
                roomCacheEntity.setProtocol(protocol);
                roomCacheDB.roomCacheDao().insertCache(roomCacheEntity);
                roomCacheDB.close();
            }
        });
        if (isBuildNewResponse) {
            return response.newBuilder()
                    .body(ResponseBody.create(MEDIA_TYPE, content))
                    .build();
        }
        return response;
    }

    private Response get200RoomResponse(Request request, RoomCacheEntity roomCacheEntity, String cacheMode) throws IOException {
        return new Response.Builder()
                .code(200)
                .request(request)
                .headers(roomCacheEntity.getResponseHeaders())
                .addHeader("Room-Cache-Control", cacheMode)
                .sentRequestAtMillis(System.currentTimeMillis())
                .receivedResponseAtMillis(System.currentTimeMillis() + 20)
                .protocol(Protocol.get(roomCacheEntity.getProtocol()))
                .message("")
                .body(ResponseBody.create(MEDIA_TYPE, roomCacheEntity.getData()))
                .build();
    }

    private Response get400Response(Request request, Headers oldHeaders, String cacheMode, String protocol) throws IOException {
        Response.Builder body = new Response.Builder()
                .code(400)
                .request(request)
                .addHeader("Room-Cache-Control", cacheMode)
                .sentRequestAtMillis(System.currentTimeMillis())
                .receivedResponseAtMillis(System.currentTimeMillis() + 20)
                .protocol(Protocol.get(protocol))
                .message("")
                .body(ResponseBody.create(MEDIA_TYPE, ""));
        if (oldHeaders == null) return body.build();
        return body.headers(oldHeaders).build();
    }

}
