package com.tobin.lib_core.base.delegate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tobin.lib_core.utils.ManifestParser;

import java.util.List;

public class ActivityDelegate implements ActivityLifecycle {

    private List<ActivityLifecycle> activityLifecycles;

    public ActivityDelegate(@NonNull Context context) {
        if (activityLifecycles == null) {
            activityLifecycles = new ManifestParser<ActivityLifecycle>(context, MetaValue.ACTIVITY_LIFECYCLE).parse();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onCreate(savedInstanceState);
            }
        }
    }

    @Override
    public void onStart() {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onStart();
            }
        }
    }

    @Override
    public void onResume() {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onPause();
            }
        }
    }

    @Override
    public void onStop() {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onStop();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onDestroy(Activity mActivity) {
        if (activityLifecycles != null) {
            for (ActivityLifecycle activity : activityLifecycles) {
                activity.onDestroy(mActivity);
            }
        }
    }
}
