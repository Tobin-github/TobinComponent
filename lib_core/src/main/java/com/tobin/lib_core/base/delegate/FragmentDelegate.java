package com.tobin.lib_core.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tobin.lib_core.utils.ManifestParser;

import java.util.List;

public class FragmentDelegate implements FragmentLifecycle {

    private List<FragmentLifecycle> fragmentLifecycles;
    private Fragment fragment;

    public FragmentDelegate(Context context, Fragment fragment) {
        this.fragment = fragment;
        if (fragmentLifecycles == null) {
            fragmentLifecycles = new ManifestParser<FragmentLifecycle>(context, MetaValue.FRAGMENT_LIFECYCLE).parse();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onAttach(context);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onCreate(savedInstanceState);
            }
        }
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onCreateView(view, savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onActivityCreate(savedInstanceState);
            }
        }
    }

    @Override
    public void onStart() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onStart();
            }
        }
    }

    @Override
    public void onResume() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onPause();
            }
        }
    }

    @Override
    public void onStop() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onStop();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onDestroyView();
            }
        }
    }

    @Override
    public void onDestroy(Fragment mFragment) {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onDestroy(mFragment);
            }
        }
    }

    @Override
    public void onDetach() {
        if (fragmentLifecycles != null && fragmentLifecycles.size() > 0) {
            for (FragmentLifecycle fragment : fragmentLifecycles) {
                fragment.onDetach();
            }
            fragmentLifecycles = null;
//            fragmentLifecycles.remove(fragment);
        }
    }

    @Override
    public boolean isAdded() {
        return fragment != null && fragment.isAdded();
    }
}
