package com.tobin.recipe.ui;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.base.BaseVMDBFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.databinding.RecipeFragmentBinding;

import timber.log.Timber;

//@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeFragment extends BaseVMDBFragment<RecipeViewModel, RecipeFragmentBinding> {

    @Override
    protected int onCreate() {
        return R.layout.recipe_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        viewModel.getRecipesClassLiveData().observe(this, recipesClassBean -> {
            Timber.tag("Tobin").i("RecipeFragment initData");
            loadService.showSuccess();
        });
    }

    @Override
    protected RecipeViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Override
    protected void showError(Object obj) {
        loadService.showSuccess();
        Toast.makeText(requireContext(),obj.toString(),Toast.LENGTH_SHORT).show();
    }
}