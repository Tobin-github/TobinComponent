package com.tobin.lib_core.constant;

public interface Constants {
    /**
     * 存放session user信息的key
     * {@link com.tobin.lib_core.session.PreferencesSessionManager}
     * {@link com.tobin.lib_core.session.MmkvSessionManager}
     */
    String KEY_SESSION_USER = "session_user";
    /**
     * 存放session token信息的key
     * {@link com.tobin.lib_core.session.PreferencesSessionManager}
     * {@link com.tobin.lib_core.session.MmkvSessionManager}
     */
    String KEY_SESSION_TOKEN = "session_token";

    /**
     * 存放 cookies
     */
    String KEY_COOKIES_TOKEN = "cookies_token";
    /**
     * room数据库默认的名字
     * {@link com.tobin.lib_core.base.Box#getRoomDataBase(Class)}
     */
    String NAME_ROOM_DATABASE = "CommonDB";

    /**
     * 默认缓存数据库名字
     */
    String ROOM_CACHE_NAME = "Room-Cache-Database";

    /**
     * 网络请求默认超时时间
     */
    int DEFAULT_TIMEOUT = 10;
}
