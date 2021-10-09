package com.tobin.recipe.ui.classify;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
import com.tobin.recipe.linkage.bean.RecipeGroupedItem;
import com.tobin.recipe.ui.ShareViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeClassFragment extends BaseFragment {
    private RecipeClassViewModel recipeClassViewModel;
    private ShareViewModel shareViewModel;
    private RecipeFragmentClassBinding binding;
    boolean isGrid = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getBinding();
        init();
    }

    protected void init() {
        recipeClassViewModel.getRecipesClassLiveData().observe(getViewLifecycleOwner(), recipesClassBean -> {
            Timber.tag("Tobin").i("RecipeFragment initData");
            if (recipesClassBean != null) {
                List<BaseGroupedItem> items = new ArrayList<>();
                for (RecipesClassBean.ResultBean resultBean : recipesClassBean.getResult()) {
                    RecipeGroupedItem groupedItem = new RecipeGroupedItem(true, resultBean.getName());
                    items.add(groupedItem);
                    for (RecipesClassBean.ResultBean.ListBean listBean : resultBean.getList()) {
                        RecipeGroupedItem.ItemInfo itemInfo = new RecipeGroupedItem.ItemInfo(listBean.getName(), resultBean.getName());
                        itemInfo.setClassid(listBean.getClassid());
                        RecipeGroupedItem groupedItem2 = new RecipeGroupedItem(itemInfo);
                        items.add(groupedItem2);
                    }
                }
                initLinkage(items);
            }
        });
    }

    private void initLinkage(List<BaseGroupedItem> items) {

        binding.ivGrid.setVisibility(View.VISIBLE);
        binding.linkageView.init(items);
        binding.linkageView.setGridMode(true);

        binding.linkageView.setDefaultOnItemBindListener((holder, view, title) -> {

        }, (primaryHolder, title) -> {

        }, (secondaryHolder, item) -> {
            ((TextView) secondaryHolder.getView(R.id.level_2_content)).setOnClickListener(v -> {
                shareViewModel.classid.postValue(((RecipeGroupedItem.ItemInfo) item.info).getClassid());
                Navigation.findNavController(v).navigate(R.id.recipe_action_recipeclassfragment_to_recipe_result_navigation);
            });

        }, (headerHolder, item) -> {

        }, (footerHolder, item) -> {

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
        shareViewModel = getActivityScopeViewModel(ShareViewModel.class);
    }


    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.recipe_fragment_class, BR.vm, recipeClassViewModel)
                .addBindingParam(BR.click, new ClickProxy());

    }

    public class ClickProxy {

        public void categoryType() {
            isGrid = !isGrid;
            binding.linkageView.setGridMode(isGrid);
        }

        public void clickSearch() {
            NavHostFragment.findNavController(RecipeClassFragment.this)
                    .navigate(R.id.recipe_action_recipeclassfragment_to_recipesearchfragment);
        }

    }

}