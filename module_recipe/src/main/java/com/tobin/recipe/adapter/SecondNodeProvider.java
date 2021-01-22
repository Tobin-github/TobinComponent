package com.tobin.recipe.adapter;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.recipe.R;
import com.tobin.recipe.ui.RecipeActivity;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class SecondNodeProvider extends BaseNodeProvider  {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recipe_class_item_node_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        // 数据类型需要自己强转
        SecondNode entity = (SecondNode) data;
        helper.setText(R.id.tv_second_item,  entity.getData().getName());
    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onChildClick(helper, view, data, position);
        Timber.d("SecondNodeProvider onChildClick position: " + position + "\nview: " + view.getId());
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        Timber.d("SecondNodeProvider onClick position: " + position + "\nview: " + view.getId());
        SecondNode secondNode = (SecondNode) data;
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_DATA, secondNode.getData());
        getContext().startActivity(intent);
//        ARouter.getInstance().build(RouterHub.RECIPE_DETAIL_ACTIVITY)
//                .withObject("class_id", secondNode.getData())
//                .navigation(getContext());

    }
}
