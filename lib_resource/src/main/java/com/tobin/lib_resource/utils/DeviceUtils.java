package com.tobin.lib_resource.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.tobin.lib_core.base.App;

/**
 * 设备工具类
 */
public class DeviceUtils {

    private static Application getApplication() {
        return App.getApp();
    }

    /**
     * IMEI （唯一标识序列号）
     * <p>需与{@link #isPhone(Context)}一起使用</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return 设备ID (优先IMEI获取不到返回AndroidId)
     */
    public static String getDeviceId() {
        String deviceId;
        if (isPhone(getApplication())) {
            deviceId = getDeviceIdIMEI();
        } else {
            deviceId = getAndroidId();
        }

        if (TextUtils.isEmpty(deviceId)){
            deviceId= "57aad8ea59dd";
        }
        return deviceId;
    }

    /**
     * 判断设备是否是手机
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取设备的IMEI
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceIdIMEI() {
        String id = null;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        }
        return id;
    }

    /**
     * 获取ANDROID ID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        return Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
