package com.tobin.recipe.adapter;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tobin.recipe.R;

import org.jetbrains.annotations.NotNull;

public class FirstNodeProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recipe_class_item_node_first;
    }

//    /**
//     * 当 ViewHolder 创建完毕以后，会执行此回掉
//     * 可以在这里做任何你想做的事情
//     */
//    @Override
//    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
//        DataBindingUtil.bind(viewHolder.itemView);
//    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        // 数据类型需要自己强转
        FirstNode entity = (FirstNode) data;
        helper.setText(R.id.tv_recipe_class, entity.getTitle());
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position);
    }
}
