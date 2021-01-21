package com.tobin.recipe.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecipeClassNodeAdapter extends BaseNodeAdapter {

    public RecipeClassNodeAdapter() {
        super();
        // 注册Provider，总共有如下三种方式

        // 需要占满一行的，使用此方法（例如section）
        addFullSpanNodeProvider(new FirstNodeProvider());
        // 普通的item provider
        addNodeProvider(new SecondNodeProvider());
        // 脚布局的 provider
//        addFooterNodeProvider(new RootFooterNodeProvider());
    }

    /**
     * 根据数据、位置等信息，返回 item 类型
     */
    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstNode) {
            return 0;
        }
        else if (node instanceof SecondNode) {
            return 1;
        }
        return -1;
    }
}