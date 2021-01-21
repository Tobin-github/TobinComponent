package com.tobin.lib_resource.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected P mPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
            initParams(getArguments());
            mPresenter = obtainPresenter();
            if (mPresenter != null) {
                getLifecycle().addObserver(mPresenter);
            }
            initView(mView);

        }
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            mView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView = null;
        mActivity = null;
        mContext = null;
        mPresenter = null;
    }

    public P getP() {
        return mPresenter;
    }

    public abstract int layoutId(Bundle savedInstanceState);

    public abstract void initParams(Bundle bundle);

    public abstract void initView(View view);

    public abstract P obtainPresenter();

}