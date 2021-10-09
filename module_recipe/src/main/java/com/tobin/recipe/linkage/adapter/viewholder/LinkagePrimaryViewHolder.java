package com.tobin.recipe.linkage.adapter.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.tobin.recipe.linkage.contract.ILinkagePrimaryAdapterConfig;

public class LinkagePrimaryViewHolder extends BaseViewHolder {

    public View mGroupTitle;
    public View mLayout;

    public LinkagePrimaryViewHolder(@NonNull View itemView, ILinkagePrimaryAdapterConfig config) {
        super(itemView);
        mGroupTitle = itemView.findViewById(config.getGroupTitleViewId());
        //need bind root layout by users, because rootLayout may not viewGroup, which can not getChild(0).
        mLayout = itemView.findViewById(config.getRootViewId());
    }
}
