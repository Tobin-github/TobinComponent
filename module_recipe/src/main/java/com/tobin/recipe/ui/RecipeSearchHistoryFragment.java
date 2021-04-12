package com.tobin.recipe.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;

@Route(path = RouterHub.RECIPE_RESULT_SEARCH_FRAGMENT)
public class RecipeSearchHistoryFragment extends BaseFragment {
    private SearchViewModel searchViewModel;

    public RecipeSearchHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeSearchHistoryFragment.
     */
    public static RecipeSearchHistoryFragment newInstance() {
        return new RecipeSearchHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_search_history, BR.vm, searchViewModel);
    }

    @Override
    protected void initViewModel() {
        searchViewModel = getFragmentScopeViewModel(SearchViewModel.class);
    }

}