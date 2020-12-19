package com.tobin.lib_core.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.tobin.lib_core.utils.ActivityUtils;

public class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private ActivityLifecycle activityLifecycle;
    private FragmentLifecycleCallbacks fragmentLifecycleCallbacks;
    private FragmentManager fm;
    private static final String TAG = "ActivityLifecycleCallback";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activityLifecycle == null) {
            activityLifecycle = new ActivityDelegate(activity);
        }
        activityLifecycle.onCreate(savedInstanceState);

        if (activity instanceof FragmentActivity) {
            fm = ((FragmentActivity) activity).getSupportFragmentManager();
            if (fragmentLifecycleCallbacks == null) {
                fragmentLifecycleCallbacks = new FragmentLifecycleCallbacks();
            }
            fm.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
        }

        //管理所有创建的Activity
        ActivityUtils.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (activityLifecycle != null) {
            activityLifecycle.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (fm != null) {
            fm.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
            fragmentLifecycleCallbacks = null;
            fm = null;
        }
        ActivityUtils.finishActivity(activity);
        if (activityLifecycle != null) {
            activityLifecycle.onDestroy(activity);
            activityLifecycle = null;
        }
    }
}
