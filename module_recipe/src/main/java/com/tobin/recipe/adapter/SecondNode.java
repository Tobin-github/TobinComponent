package com.tobin.recipe.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.tobin.recipe.bean.RecipesClassBean;

import java.util.List;

/**
 * 第二个节点SecondNode，里面没有子节点了
 */
public class SecondNode extends BaseNode {

    private RecipesClassBean.ResultBean.ListBean listBean;

    public SecondNode(RecipesClassBean.ResultBean.ListBean listBean) {
        this.listBean = listBean;
    }

    public  RecipesClassBean.ResultBean.ListBean getData() {
        return listBean;
    }

    /**
     * 重写此方法，返回子节点
     */
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}