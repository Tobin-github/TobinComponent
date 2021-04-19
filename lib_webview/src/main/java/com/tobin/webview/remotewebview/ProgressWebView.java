package com.tobin.webview.remotewebview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tobin.webview.remotewebview.progressbar.IndicatorHandler;
import com.tobin.webview.remotewebview.progressbar.WebProgressBar;
import com.tobin.webview.remotewebview.webchromeclient.ProgressWebChromeClient;

public class ProgressWebView extends BaseWebView {

    public ProgressWebView(Context context) {
        super(context);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private IndicatorHandler indicatorHandler;

    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int progress = (int) msg.obj;
            indicatorHandler.progress(progress);
            return true;
        }
    });

    @Override
    public Handler getHandler() {
        return handler;
    }

    private void init() {
        WebProgressBar progressBar = new WebProgressBar(context);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // progressBar.setVisibility(GONE);
        addView(progressBar);
        indicatorHandler = IndicatorHandler.getInstance().injectProgressView(progressBar);
        setWebChromeClient(new ProgressWebChromeClient(handler));
    }
}
