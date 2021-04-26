package com.tobin.recipe.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tobin.lib_core.base.Box;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;
import com.tobin.recipe.databinding.RecipeFragmentSearchBinding;

import timber.log.Timber;

/**
 * Created by Tobin on 2021/4/20
 * Email: 616041023@qq.com
 * Description:
 */
public class RecipeSearchFragment extends BaseFragment {
    private RecipeSearchViewModel viewModel;
    private RecipeFragmentSearchBinding binding;

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
    }

    @Override
    protected void initViewModel() {
        viewModel = getFragmentScopeViewModel(RecipeSearchViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_search, BR.vm, viewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (RecipeFragmentSearchBinding) getBinding();
        init();
    }

    protected void init() {
        binding.recipeSearch.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Timber.tag("Tobin").e("EditorInfo.IME_ACTION_SEARCH");
                String inputData = binding.recipeSearch.getText().toString();
                if (TextUtils.isEmpty(inputData)) {
                    return true;
                }
                //隐藏光标和软键盘
                binding.recipeSearch.clearFocus();
                hideKeyboard(view);
                viewModel.recipesSearch(inputData);
                return true;
            }
            return false;
        });

        viewModel.getRecipesSearchLiveData().observe(getViewLifecycleOwner(), recipesBean -> {

        });

    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) Box.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    public class ClickProxy {

        public void goSearch(){
            String inputData = binding.recipeSearch.getText().toString();
            if (TextUtils.isEmpty(inputData)) {
                return;
            }
            //隐藏光标和软键盘
            binding.recipeSearch.clearFocus();
            hideKeyboard(binding.recipeSearch);
            viewModel.recipesSearch(inputData);

            Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_search_container);
            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.recipe_action_recipesearchhistoryfragment_to_recipe_result_navigation);
        }

    }

}