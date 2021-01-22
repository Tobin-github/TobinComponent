package com.tobin.recipe.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseActivity;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.databinding.RecipeActivitySearchBinding;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivity<SearchViewModel, RecipeActivitySearchBinding>{
    private RecipesBean recipesBean1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null){
//            gotoFragment(RouterHub.RECIPE_RESULT_SEARCH_FRAGMENT);
//        }
//    }

    private void gotoFragment(String path){
//        getSupportFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
////                .replace(R.id.fragment_search_container,new RecipeResultFragment())
////                    .replace(R.id.fragment_search_container,new RecipeClassFragment())
////                    .replace(R.id.fragment_search_container,new RecipeSearchHistoryFragment())
//                .replace(R.id.fragment_search_container, fragment)
//                .commitNow();

        Fragment fragment = (Fragment) ARouter.getInstance().build(path)
                .withSerializable("recipesBean",recipesBean1)
                .navigation();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_search_container, fragment)
                .commitNow();

    }

    @Override
    protected int onCreate() {
        return R.layout.recipe_activity_search;
    }

    @Override
    protected void initView() {
        dataBinding.recipeSearch.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Timber.tag("Tobin").e("EditorInfo.IME_ACTION_SEARCH");
                String inputData = dataBinding.recipeSearch.getText().toString();
                if (TextUtils.isEmpty(inputData)) {
                    return true;
                }

                //隐藏光标和软键盘
                dataBinding.recipeSearch.clearFocus();
                hideKeyboard(view);

                viewModel.recipesSearch(inputData);

                return true;
            }
            return false;
        });

    }

    @Override
    protected void initData() {
        viewModel.getRecipesSearchLiveData().observe(this, recipesBean -> {
            recipesBean1 = recipesBean;
            gotoFragment(RouterHub.RECIPE_RESULT_FRAGMENT);
        });

        gotoFragment(RouterHub.RECIPE_RESULT_FRAGMENT);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    protected SearchViewModel initViewModel() {
        return new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }
}