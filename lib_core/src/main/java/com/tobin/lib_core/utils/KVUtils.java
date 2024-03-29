package com.tobin.lib_core.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tobin.lib_core.base.Box;
import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * 使用腾讯的MMKV替换原生的SharedPreferences
 */
public final class KVUtils {
    public static void init(Context application) {
        MMKV.initialize(application.getApplicationContext());
    }

    public static boolean put(String key, Object value) {
        return put(null, key, value);
    }

    public static boolean put(String fileID, String key, Object value) {
        return put(fileID, false, key, value);
    }

    public static boolean put(String fileID, boolean isMultiProcess, String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        MMKV mmkv;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            if (isMultiProcess) {
                mmkv = MMKV.mmkvWithID(fileID, MMKV.MULTI_PROCESS_MODE);
            } else {
                mmkv = MMKV.mmkvWithID(fileID, MMKV.SINGLE_PROCESS_MODE);
            }
        }
        boolean isSuccess = false;

        if (value instanceof byte[]) {
            isSuccess = mmkv.encode(key, (byte[]) value);
        } else if (value instanceof Integer) {
            isSuccess = mmkv.encode(key, (Integer) value);
        } else if (value instanceof Float) {
            isSuccess = mmkv.encode(key, (Float) value);
        } else if (value instanceof Double) {
            isSuccess = mmkv.encode(key, (Double) value);
        } else if (value instanceof Long) {
            isSuccess = mmkv.encode(key, (Long) value);
        } else if (value instanceof String) {
            isSuccess = mmkv.encode(key, (String) value);
        } else if (value instanceof Boolean) {
            isSuccess = mmkv.encode(key, (Boolean) value);
        }
        return isSuccess;
    }

    public static boolean put(String key, Set<String> value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.encode(key, value);
    }


    public static Object get(String key, @Nullable Object defaultObject) {
        return get(null, key, defaultObject);
    }

    public static Object get(String fileID, String key, Object defaultObject) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        MMKV mmkv = null;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            mmkv = MMKV.mmkvWithID(fileID);
        }
        if (defaultObject instanceof byte[]) {
            return mmkv.decodeBytes(key);
        } else if (defaultObject instanceof Integer) {
            return mmkv.decodeInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mmkv.decodeFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Double) {
            return mmkv.decodeDouble(key, (Double) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mmkv.decodeLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof String) {
            return mmkv.decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mmkv.decodeBool(key, (Boolean) defaultObject);
        }
        return null;
    }

    public static <T> T getEntity(String key, Class<T> tClass) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String kv = (String) get(null, key, "");
        if (TextUtils.isEmpty(kv))
            return null;
        return Box.getGson().fromJson(kv, tClass);
    }

    public static void putEntity(String key, Object object) {
        if (TextUtils.isEmpty(key) || object == null) {
            return;
        }
        put(key, Box.getGson().toJson(object));
    }

    public static Set<String> getStringSet(String key, @NonNull Set<String> defaultStringSet) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        MMKV mmkv = MMKV.defaultMMKV();
        if (mmkv == null) {
            return defaultStringSet;
        }
        return mmkv.decodeStringSet(key, defaultStringSet);
    }

    public static Set<String> getStringSet(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeStringSet(key);
    }

    public static void remove(String key) {
        remove(null, key);
    }

    public static void remove(String fileID, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        MMKV mmkv;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            mmkv = MMKV.mmkvWithID(fileID);
        }
        mmkv.remove(key);
    }

    public static void removeValue(String key) {
        removeValue(null, key);
    }

    public static void removeValue(String fileID, String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        MMKV mmkv;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            mmkv = MMKV.mmkvWithID(fileID);
        }
        mmkv.removeValueForKey(key);
    }

    public static void removeValues(String[] keys) {
        removeValues(null, keys);
    }

    public static void removeValues(String fileID, String[] keys) {
        if (keys == null) {
            return;
        }
        MMKV mmkv;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            mmkv = MMKV.mmkvWithID(fileID);
        }
        mmkv.removeValuesForKeys(keys);
    }

    public static void clear() {
        clear(null);
    }

    public static void clear(String fileID) {
        MMKV mmkv;
        if (null == fileID) {
            mmkv = MMKV.defaultMMKV();
        } else {
            mmkv = MMKV.mmkvWithID(fileID);
        }
        mmkv.clear();
    }
}
