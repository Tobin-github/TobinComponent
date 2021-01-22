package com.tobin.recipe.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.adapter.RecipeResultAdapter;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.bean.RecipesClassBean.ResultBean.ListBean;
import com.tobin.recipe.databinding.RecipeFragmentResultBinding;
import com.tobin.recipe.widgets.RecycleViewDivider;

@Route(path = RouterHub.RECIPE_RESULT_FRAGMENT)
public class RecipeResultFragment extends BaseFragment<RecipeResultViewModel, RecipeFragmentResultBinding> {
    @Autowired
    public RecipesBean recipesBean;

    @Autowired
    public ListBean listBean;

    RecipeResultAdapter adapter;

    public static RecipeResultFragment newInstance(RecipesBean recipesBean) {
        RecipeResultFragment fragment = new RecipeResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipesBean", recipesBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static RecipeResultFragment newInstance(ListBean listBean) {
        RecipeResultFragment fragment = new RecipeResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listBean", listBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int onCreate() {
        return R.layout.recipe_fragment_result;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("recipesBean")) {
                this.recipesBean = (RecipesBean) bundle.getSerializable("recipesBean");
            }
            if (bundle.containsKey("listBean")) {
                this.listBean = (ListBean) bundle.getSerializable("listBean");
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        dataBinding.rcvRecipeResult.setLayoutManager(layoutManager);
        dataBinding.rcvRecipeResult.addItemDecoration(new RecycleViewDivider(
                activity, LinearLayoutManager.HORIZONTAL, 12, android.R.color.white));
        adapter = new RecipeResultAdapter();
        dataBinding.rcvRecipeResult.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showLoading();
        viewModel.getRecipesLiveData().observe(this, recipesBean -> {
            if (recipesBean != null) {
                this.recipesBean = recipesBean;
                adapter.addData(recipesBean.getResult().getList());
                showSuccess();
            } else {
                showEmpty();
            }
        });

        if (listBean != null) {
            viewModel.byRecipesClass(Integer.parseInt(listBean.getClassid()), 0, 20);
        } else if (recipesBean != null) {
            adapter.addData(recipesBean.getResult().getList());
            showSuccess();
        } else {
            viewModel.byRecipesClass(5, 0, 20);
        }

    }

    @Override
    protected RecipeResultViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeResultViewModel.class);
    }

    @Override
    protected void showError(Object obj) {

    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }
}