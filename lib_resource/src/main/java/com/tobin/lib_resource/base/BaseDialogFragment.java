package com.tobin.lib_resource.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.tobin.lib_resource.R;

import java.util.Objects;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description: DialogFragment基类
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fragment_dialog_style);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getViewLifecycleOwnerLiveData().observe(this, lifecycleOwner -> {
            if (lifecycleOwner != null){

                Timber.tag("lib_resource").i("BaseDialogFragment： lifecycleOwner state %s",
                        lifecycleOwner.getLifecycle().getCurrentState().toString());

            }else {
                Timber.tag("lib_resource").i("BaseDialogFragment: lifecycleOwner is null");
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(false);
    }
}
