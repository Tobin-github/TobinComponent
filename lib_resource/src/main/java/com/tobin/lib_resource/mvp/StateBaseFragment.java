package com.tobin.lib_resource.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.tobin.lib_core.utils.NetworkUtils;
import com.tobin.lib_resource.R;
import com.tobin.lib_resource.dialog.FDialog;
import com.tobin.lib_resource.loadsir.ErrorCallback;
import com.tobin.lib_resource.loadsir.LottieEmptyCallback;
import com.tobin.lib_resource.loadsir.LottieLoadingCallback;
import com.tobin.lib_resource.loadsir.TimeoutCallback;
import com.tobin.lib_resource.mvp.base.BaseFragment;
import com.tobin.lib_resource.mvp.base.IPresenter;

/**
 *
 * description: 二次封装的带状态页面的BaseFragment
 */
public abstract class StateBaseFragment<P extends IPresenter> extends BaseFragment {
    private LoadService mStateView;
    private FDialog progressLoadingDialog;

    @Override
    public P getP() {
        return (P) super.getP();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = layoutId(savedInstanceState);
        if (layoutId <= 0) {
            throw new IllegalArgumentException("layout is null");
        }
        if (mView == null) {
            mView = inflater.inflate(layoutId, container, false);
//            //初始化状态页面
//            Object rootView = placeView();
//            if (rootView == null) {
//                rootView = mView;
//            }
//            initStateView(rootView);
            //初始化基本参数
            initParams(getArguments());
            //初始化Presenter
            mPresenter = obtainPresenter();
//            if (mPresenter == null || !(mPresenter instanceof LifecycleObserver)) {
//                throw new IllegalArgumentException("obtain a wrong presenter");
//            }
            if (mPresenter != null) {
                getLifecycle().addObserver(mPresenter);
            }
            //初始化控件id
            initView(mView);
        }
        //判断网络是否可用
        if (!NetworkUtils.isAvailable()) {
            showNetError();
        }
        return mView;
    }

    @CallSuper
    @Override
    public void loadDataSuccess(Object... objects) {
        showSuccess();
    }

    @Override
    public void loadDataError(Object... objects) {
        showError();
    }

    @Override
    public void loadDataEmpty() {
        showEmpty();
    }

    @Override
    public void onNetworkError() {
        showNetError();
    }

    private void initStateView(Object rootView) {
        mStateView = LoadSir.getDefault().register(rootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                reloadData(v);
            }
        }).setCallBack(LottieEmptyCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                customEmptyPage(context, view);
            }
        }).setCallBack(ErrorCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                customErrorPage(context, view);
            }
        }).setCallBack(LottieEmptyCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                customLoadingPage(context, view);
            }
        }).setCallBack(TimeoutCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                customNetErrorPage(context, view);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgressLoading();
    }

    protected View placeView() {
        return null;
    }

    protected void customEmptyPage(Context context, View view) {

    }

    protected void customErrorPage(Context context, View view) {

    }

    protected void customLoadingPage(Context context, View view) {

    }

    protected void customNetErrorPage(Context context, View view) {

    }

    /**
     * 重试
     *
     * @param view
     */
    public void reloadData(View view) {

    }

    public void showLoading() {
        if (mStateView != null) {
            mStateView.showCallback(LottieLoadingCallback.class);
        }
    }

    public void showEmpty() {
        if (mStateView != null) {
            mStateView.showCallback(LottieEmptyCallback.class);
        }
    }

    public void showError() {
        if (mStateView != null) {
            mStateView.showCallback(ErrorCallback.class);
        }
    }

    public void showSuccess() {
        if (mStateView != null) {
            mStateView.showSuccess();
        }
    }

    public void showNetError() {
        if (mStateView != null) {
            mStateView.showCallback(TimeoutCallback.class);
        }
    }

    public void showProgressLoading() {
        progressLoadingDialog = FDialog.build()
                .setSupportFM(getFragmentManager())
                .setLayoutId(R.layout.dialog_layout_loading)
                .setDimAmount(1)
                .show();
    }

    public void dismissProgressLoading() {
        if (progressLoadingDialog != null) {
            progressLoadingDialog.dismiss();
        }
        progressLoadingDialog = null;
    }
}
