package com.tobin.life.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.life.R;
import com.tobin.webview.WebActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        root.findViewById(R.id.test_webview).setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), WebActivity.class));

            WebActivity.startCommonWeb(getActivity(),"js native","file:///android_asset/TestWebView.html");
//            WebActivity.startCommonWeb(getActivity(),"js native","https://blog.csdn.net/jinmie0193/article/details/80723724");
        });

        root.findViewById(R.id.recipe_activity).setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(RouterHub.RECIPE_RECIPE_ACTIVITY)
                    .navigation(getActivity());
        });

        return root;
    }
}