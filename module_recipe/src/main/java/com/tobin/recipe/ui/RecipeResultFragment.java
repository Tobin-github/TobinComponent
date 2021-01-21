package com.tobin.recipe.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.adapter.RecipeResultAdapter;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean;
import com.tobin.recipe.databinding.RecipeFragmentResultBinding;
import com.tobin.recipe.widgets.RecycleViewDivider;

import timber.log.Timber;

public class RecipeResultFragment extends BaseFragment<RecipeResultViewModel, RecipeFragmentResultBinding> {

    private RecipesClassBean.ResultBean.ListBean listBean;

    private RecipesBean recipesBean;
    RecipeResultAdapter adapter;

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        dataBinding.rcvRecipeResult.setLayoutManager(layoutManager);
        dataBinding.rcvRecipeResult.addItemDecoration(new RecycleViewDivider(
                activity, LinearLayoutManager.HORIZONTAL, 12, android.R.color.white));
        adapter = new RecipeResultAdapter();
        dataBinding.rcvRecipeResult.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        if (listBean != null) {
            viewModel.byRecipesClass(Integer.parseInt(listBean.getClassid()), 0, 20);
            viewModel.getRecipesLiveData().observe(this, recipesBean -> {
                if (recipesBean != null) {
                    this.recipesBean = recipesBean;
                    adapter.addData(recipesBean.getResult().getList());
                    Timber.d("RecipeResultFragment initData recipesBean: " + recipesBean.toString());
                }
            });
        }
    }

    @Override
    protected int onCreate() {
        return R.layout.recipe_fragment_result;
    }

    @Override
    protected RecipeResultViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeResultViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }

}