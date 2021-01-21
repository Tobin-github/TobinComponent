package com.tobin.recipe.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesClassBean;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Tobin on 2021/1/20
 * Email: 616041023@qq.com
 * Description:
 */
public class RecipeClassLeftAdapter extends BaseQuickAdapter<RecipesClassBean.ResultBean, BaseViewHolder> {

    public RecipeClassLeftAdapter() {
        super(R.layout.recipe_item_search_sort_left);
    }

    private int mSelectedPosition;
    public void setSelected(int position){
        if (position > getData().size()) return;
        getItem(mSelectedPosition).setSelected(false);
        notifyItemChanged(mSelectedPosition);
        getItem(position).setSelected(true);
        notifyItemChanged(position);
        mSelectedPosition = position;
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull RecipesClassBean.ResultBean item) {
        helper.setText(R.id.tv_left, item.getName());
        helper.setVisible(R.id.view_mark,item.isSelected());
    }
}