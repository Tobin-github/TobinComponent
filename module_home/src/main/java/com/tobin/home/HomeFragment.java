package com.tobin.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.tobin.home.databinding.HomeFragmentHomeBinding;
import com.tobin.lib_core.http.download.RxDownloadUtil;
import com.tobin.lib_core.http.subscriber.DownLoadSubscriber;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.webview.WebActivity;

import timber.log.Timber;

@Route(path = RouterHub.APP_HOME_FRAGMENT)
public class HomeFragment extends BaseFragment {

    HomeFragmentHomeBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (HomeFragmentHomeBinding) getBinding();
    }

    protected void initData() {
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    }

    HomeViewModel homeViewModel;

    @Override
    protected void initViewModel() {
        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.home_fragment_home, BR.vm, homeViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    public class ClickProxy {
        public void downFile() {
            RxDownloadUtil.downLoadFile("https://www.ajzhan.com/uploads/apk/AjZhan_1.0.0_0000.apk")
                    .subscribe(new DownLoadSubscriber() {
                        @Override
                        protected void _onNext(String result) {
                            Timber.tag("Tobin").e("_onNext result: %s ", result);
                        }

                        @Override
                        protected void _onProgress(Integer percent) {
                            Timber.tag("Tobin").e("_onNext percent: %s ", percent);
                        }

                        @Override
                        public void onComplete() {
                            Timber.tag("Tobin").e("complete");
                        }
                    });
        }

        public void downFileCancel() {

        }

        public void openWeb() {
//            WebActivity.startCommonWeb(getActivity(), "js native", "file:///android_asset/TestWebView.html");
            String url = "https://blog.csdn.net/jinmie0193/article/details/80723724";
            WebActivity.startCommonWeb(getActivity(), "Blog", url);
        }

        public void recipePage() {
            ARouter.getInstance()
                    .build(RouterHub.RECIPE_RECIPE_ACTIVITY)
                    .navigation(getActivity());
        }

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}