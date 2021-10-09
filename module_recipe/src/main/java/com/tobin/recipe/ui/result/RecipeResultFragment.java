package com.tobin.recipe.ui.result;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tobin.lib_resource.arouter.RouterHub;

import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;

import com.tobin.recipe.databinding.RecipeFragmentResultBinding;
import com.tobin.recipe.ui.ShareViewModel;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_RESULT_FRAGMENT)
public class RecipeResultFragment extends BaseFragment {

    RecipeResultViewModel viewModel;
    ShareViewModel shareViewModel;
    RecipeFragmentResultBinding binding;

    @Override
    protected void initViewModel() {
        viewModel = getFragmentScopeViewModel(RecipeResultViewModel.class);
        shareViewModel = getActivityScopeViewModel(ShareViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_result, BR.vm, viewModel)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.adapter, new RecipeResultAdapter(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (RecipeFragmentResultBinding) getBinding();

        initData();
    }

    protected void initData() {
        shareViewModel.classid.observe(getViewLifecycleOwner(), s -> {
            Timber.tag("Tobin").d("shareViewModel classid observe %s", s);
            viewModel.byRecipesClass(Integer.parseInt(s), 0, 20);
        });

        binding.getAdapter().setOnItemClickListener((viewId, item, position) -> {
            NavHostFragment.findNavController(requireParentFragment())
                    .navigate(R.id.recipe_action_reciperesultfragment_to_recipe_detail_navigation);
        });

    }


    public class ClickProxy {

    }

}