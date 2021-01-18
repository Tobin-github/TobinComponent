package com.tobin.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tobin.home.databinding.FragmentHomeBinding;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.webview.WebActivity;

@Route(path = RouterHub.APP_HOME_FRAGMENT)
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int onCreate() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        dataBinding.testWebview.setOnClickListener(v -> {
            //            startActivity(new Intent(getContext(), WebActivity.class));
            WebActivity.startCommonWeb(getActivity(),"js native","file:///android_asset/TestWebView.html");
//            WebActivity.startCommonWeb(getActivity(),"js native","https://blog.csdn.net/jinmie0193/article/details/80723724");
        });


        dataBinding.recipeActivity.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(RouterHub.RECIPE_RECIPE_ACTIVITY)
                    .navigation(getActivity());
        });
    }

    @Override
    protected void initData() {
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                dataBinding.textHome.setText(s);
            }
        });
    }

    @Override
    protected HomeViewModel initViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}