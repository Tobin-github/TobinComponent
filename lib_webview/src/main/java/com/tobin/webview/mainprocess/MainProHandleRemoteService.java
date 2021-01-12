package com.tobin.webview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class MainProHandleRemoteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int pid = android.os.Process.myPid();
        Timber.tag("Tobin").d("MainProHandleRemoteService: %s", "当前进程ID为："
                + pid + "----" + "客户端与服务端连接成功，服务端返回BinderPool.BinderPoolImpl 对象");
        return new RemoteWebBinderPool.BinderPoolImpl(this);
    }
}
