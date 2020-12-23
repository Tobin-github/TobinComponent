package com.tobin.webview;

import android.os.Bundle;

import com.tobin.webview.basefragment.BaseWebViewFragment;
import com.tobin.webview.utils.WebConstants;

public class CommonWebFragment extends BaseWebViewFragment {

    public static CommonWebFragment newInstance(String url) {
        CommonWebFragment fragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_common_webview;
    }

    @Override
    public int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
