package com.tobin.webview.mainprocess;

import android.content.Context;
import android.os.RemoteException;

import com.tobin.webview.IWebAidlCallback;
import com.tobin.webview.IWebAidlInterface;
import com.tobin.webview.command.mainprocess.MainProcessCommandsManager;

import com.google.gson.Gson;

import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class MainProAidlInterface extends IWebAidlInterface.Stub {

    private Context context;

    public MainProAidlInterface(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void handleWebAction(int level, String actionName, String jsonParams, IWebAidlCallback callback) throws RemoteException {
        int pid = android.os.Process.myPid();
        Timber.tag("MainProAidlInterface").d(String.format(Locale.getDefault(),"进程ID（%d）， WebView请求(%s), 参数 （%s）", pid, actionName, jsonParams));
        try {
            handleRemoteAction(level, actionName, new Gson().fromJson(jsonParams, Map.class), callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRemoteAction(int level, final String actionName, Map paramMap, final IWebAidlCallback callback) throws Exception {
        MainProcessCommandsManager.getInstance().findAndExecRemoteCommand(
                context, level, actionName, paramMap,
                (int status, String action, Object result) -> {
                    try {
                        if (callback != null) {
                            callback.onResult(status, actionName, new Gson().toJson(result));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
