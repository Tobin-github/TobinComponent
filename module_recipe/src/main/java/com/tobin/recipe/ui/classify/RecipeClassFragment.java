package com.tobin.recipe.ui.classify;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;

import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig;
import com.tobin.recipe.BR;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesClassBean;
import com.tobin.recipe.databinding.RecipeFragmentClassBinding;
import com.tobin.recipe.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeClassFragment extends BaseFragment {
    private RecipesClassBean recipesClassBean;
    private RecipeClassViewModel recipeClassViewModel;
    RecipeFragmentClassBinding binding;
    boolean isGrid = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (RecipeFragmentClassBinding) getBinding();

        binding.ivGrid.setOnClickListener(v -> {
            if (isGrid) {
                binding.linkageView.setGridMode(false);
            } else {
                binding.linkageView.setGridMode(true);
            }
            isGrid = !isGrid;

        });
    }

    @Override
    protected void loadInitData() {
        super.loadInitData();
        recipeClassViewModel.getRecipesClassLiveData().observe(this, recipesClassBean -> {
            Timber.tag("Tobin").i("RecipeFragment initData");
            if (recipesClassBean != null) {
                this.recipesClassBean = recipesClassBean;
                List<BaseGroupedItem> items = new ArrayList<>();
                for (RecipesClassBean.ResultBean resultBean : recipesClassBean.getResult()) {
                    BaseGroupedItem groupedItem = new BaseGroupedItem(true, resultBean.getName());
                    items.add(groupedItem);
                    for (RecipesClassBean.ResultBean.ListBean listBean : resultBean.getList()) {
                        BaseGroupedItem.ItemInfo itemInfo = new BaseGroupedItem.ItemInfo(listBean.getName(), resultBean.getName());
                        BaseGroupedItem groupedItem2 = new BaseGroupedItem(itemInfo);
                        items.add(groupedItem2);
                    }
                }
                binding.ivGrid.setVisibility(View.VISIBLE);
                binding.linkageView.init(items);
                binding.linkageView.setGridMode(true);
            }
        });
    }

    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initViewModel() {
        recipeClassViewModel = getFragmentScopeViewModel(RecipeClassViewModel.class);
    }


    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_class, BR.vm, recipeClassViewModel)
                .addBindingParam(BR.click, new ClickProxy());

    }

    public class ClickProxy {

    }


}