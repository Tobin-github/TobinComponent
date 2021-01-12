package com.tobin.webview.basefragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tobin.lib_resource.loadsir.ErrorCallback;
import com.tobin.lib_resource.loadsir.LottieLoadingCallback;

import timber.log.Timber;
import com.tobin.webview.R;
import com.tobin.webview.remotewebview.BaseWebView;
import com.tobin.webview.remotewebview.callback.WebViewCallBack;
import com.tobin.webview.utils.WebConstants;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class BaseWebViewFragment extends BaseFragment implements WebViewCallBack {
    public static final String ACCOUNT_INFO_HEADERS = "account_header";

    private BaseWebView webView;
    private HashMap<String, String> accountInfoHeaders;
    private String webUrl;
    private LoadService loadService;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            webUrl = bundle.getString(WebConstants.INTENT_TAG_URL);
            if (bundle.containsKey(ACCOUNT_INFO_HEADERS)) {
                accountInfoHeaders = (HashMap<String, String>) bundle.getSerializable(ACCOUNT_INFO_HEADERS);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        webView = view.findViewById(R.id.web_view);
        if (accountInfoHeaders != null) {
            webView.setHeaders(accountInfoHeaders);
        }

        loadService = LoadSir.getDefault().register(webView, (Callback.OnReloadListener) v -> {
            Timber.tag("BaseWebViewFragment").w("onReload ");
            loadService.showCallback(LottieLoadingCallback.class);
            // your retry logic
            loadUrl();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.registerWebViewCallBack(this);
        CommandDispatcher.getInstance().initAidlConnect(mContext);
        loadUrl();
    }

    protected void loadUrl() {
        webView.loadUrl(webUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.dispatchEvent("pageResume");
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.dispatchEvent("pagePause");
        webView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        webView.dispatchEvent("pageStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.dispatchEvent("pageDestroy");
        clearWebView(webView);
    }


    @Override
    public int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }

    @Override
    public void pageStarted(String url) {
        loadService.showCallback(LottieLoadingCallback.class);
    }

    @Override
    public void pageFinished(String url) {
        loadService.showSuccess();
    }

    @Override
    public boolean overrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onError(String description) {
        loadService.showCallback(ErrorCallback.class);
    }

    @Override
    public void exec(Context context, int commandLevel, String cmd, String params, WebView webView) {
        CommandDispatcher.getInstance().exec(context, commandLevel, cmd, params, webView, getDispatcherCallBack());
    }

    protected CommandDispatcher.DispatcherCallBack getDispatcherCallBack() {
        return null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return onBackHandle();
        }
        return false;
    }

    public boolean onBackPressed(){
        return onBackHandle();
    }

    protected boolean onBackHandle() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void clearWebView(WebView webView) {
        if (webView == null) return;
        if (Looper.myLooper() != Looper.getMainLooper()) return;

        Timber.tag("Tobin").w("clearWebView %s", Looper.myLooper());
        webView.stopLoading();
        if (webView.getHandler() != null) {
            webView.getHandler().removeCallbacksAndMessages(null);
        }
        webView.removeAllViews();
        ViewGroup mViewGroup;
        if ((mViewGroup = ((ViewGroup) webView.getParent())) != null) {
            mViewGroup.removeView(webView);
        }
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.setTag(null);
        webView.clearHistory();
        webView.destroy();
        webView = null;
    }
}
