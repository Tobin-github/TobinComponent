package com.tobin.recipe.ui.search;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;

@Route(path = RouterHub.RECIPE_RESULT_SEARCH_FRAGMENT)
public class RecipeSearchHistoryFragment extends BaseFragment {
    private RecipeSearchViewModel searchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_search_history, BR.vm, searchViewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void initViewModel() {
        searchViewModel = getFragmentScopeViewModel(RecipeSearchViewModel.class);
    }

    public class ClickProxy {



    }

}