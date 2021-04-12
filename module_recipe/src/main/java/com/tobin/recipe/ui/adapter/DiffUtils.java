package com.tobin.recipe.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.tobin.recipe.bean.RecipesBean;

public class DiffUtils {

    private DiffUtil.ItemCallback<RecipesBean.ResultBean.ListBean> mRecipeResultCallback;

    private DiffUtils() {
    }

    private static final DiffUtils S_DIFF_UTILS = new DiffUtils();

    public static DiffUtils getInstance() {
        return S_DIFF_UTILS;
    }


    public DiffUtil.ItemCallback<RecipesBean.ResultBean.ListBean> getRecipeResultCallback() {
        if (mRecipeResultCallback == null) {
            mRecipeResultCallback = new DiffUtil.ItemCallback<RecipesBean.ResultBean.ListBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull RecipesBean.ResultBean.ListBean oldItem,
                                               @NonNull RecipesBean.ResultBean.ListBean newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull RecipesBean.ResultBean.ListBean oldItem,
                                                  @NonNull RecipesBean.ResultBean.ListBean newItem) {
                    return oldItem.getClassid() == newItem.getClassid();
                }
            };
        }
        return mRecipeResultCallback;
    }
}
