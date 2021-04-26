package com.tobin.recipe.ui.result;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tobin.lib_resource.mvvm.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesBean;
import com.tobin.recipe.databinding.RecipeResultItemBinding;

import timber.log.Timber;

public class RecipeResultAdapter extends BaseDataBindingAdapter<RecipesBean.ResultBean.ListBean, RecipeResultItemBinding> {

    public RecipeResultAdapter(Context context) {
        super(context, DiffUtils.getInstance().getRecipeResultCallback());
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recipe_result_item;
    }

    @Override
    protected void onBindItem(RecipeResultItemBinding binding, RecipesBean.ResultBean.ListBean item, RecyclerView.ViewHolder holder) {

        binding.tvRecipeTitle.setText(item.getName());
        binding.tvRecipeContent.setText(item.getTag());
        Timber.d("getPic: %s", item.getPic());

        //设置图片大小
        RoundedCorners roundedCorners = new RoundedCorners(6);

        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(new ColorDrawable(Color.GRAY))//设置占位图
                .error(R.mipmap.ic_empty_pic)// 设置错误图片
                .fallback(new ColorDrawable(Color.RED))
                .override(300, 300);//采样率

        Glide.with(binding.getRoot().getContext())
                .load(item.getPic())
                .apply(options)
                .into(binding.ivRecipeIcon);

    }


}