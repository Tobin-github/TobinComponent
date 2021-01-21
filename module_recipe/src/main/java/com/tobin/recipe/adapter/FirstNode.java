package com.tobin.recipe.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.tobin.recipe.bean.RecipesClassBean;

import java.util.List;

/**
 * 第一个节点FirstNode，里面放子节点SecondNode
 */
public class FirstNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private RecipesClassBean.ResultBean resultBean;

    public FirstNode(List<BaseNode> childNode, RecipesClassBean.ResultBean resultBean) {
        this.childNode = childNode;
        this.resultBean = resultBean;
    }

    public String getTitle() {
        return resultBean.getName();
    }

    public RecipesClassBean.ResultBean getResultBean(){
        return resultBean;
    }

    /**
     * 重写此方法，返回子节点
     */
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
