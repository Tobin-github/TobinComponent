package com.tobin.lib_core.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * {@link Activity} 代理类,用于框架内部在每个 {@link Activity} 的对应生命周期中插入需要的逻辑
 * @see ActivityDelegate
 */
public interface ActivityLifecycle {

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroy(Activity activity);
}
