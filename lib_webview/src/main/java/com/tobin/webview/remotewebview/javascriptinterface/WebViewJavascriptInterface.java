package com.tobin.webview.remotewebview.javascriptinterface;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import timber.log.Timber;

/**
 *
 * 1. 保留command的注册
 * 2. 不支持command通过远程aidl方式调用
 */
public final class WebViewJavascriptInterface {

    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.myLooper());
    private JavascriptCommand javascriptCommand;

    public WebViewJavascriptInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void post(final String cmd, final String param) {
        mHandler.post(() -> {
            try {
                if (javascriptCommand != null) {
                    Timber.tag("JavascriptInterface").e("cmd: " + cmd + "\nparam" + param);
                    javascriptCommand.exec(mContext, cmd, param);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setJavascriptCommand(JavascriptCommand javascriptCommand) {
        this.javascriptCommand = javascriptCommand;
    }

    public interface JavascriptCommand {
        void exec(Context context, String cmd, String params);
    }
}
