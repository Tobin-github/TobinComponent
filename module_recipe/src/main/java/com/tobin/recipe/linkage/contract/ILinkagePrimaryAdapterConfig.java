package com.tobin.recipe.linkage.contract;

import android.content.Context;
import android.view.View;

import com.tobin.recipe.linkage.adapter.viewholder.LinkagePrimaryViewHolder;

public interface ILinkagePrimaryAdapterConfig {

    /**
     * setContext
     *
     * @param context context
     */
    void setContext(Context context);

    /**
     * get layout res id
     *
     * @return layout res id
     */
    int getLayoutId();

    /**
     * get textView id of layout
     *
     * @return textView id of layout
     */
    int getGroupTitleViewId();

    /**
     * get rootView id of layout
     *
     * @return rootView id of layout
     */
    int getRootViewId();

    /**
     * achieve the onBindViewHolder logic on outside
     * <p>
     * Note: Do not setOnClickListener in onBindViewHolder,
     * instead, you can deal with item click in method 'ILinkagePrimaryAdapterConfig.onItemSelected()'
     * or 'LinkageRecyclerView.OnPrimaryItemClickListener.onItemClick()'
     * <p>
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkagePrimaryViewHolder
     * @param title    title of this position
     * @param selected selected of this position
     */
    void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title);

    /**
     * on primary item clicked
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder LinkagePrimaryViewHolder
     * @param view   itemView
     * @param title  title of primary item
     */
    void onItemClick(LinkagePrimaryViewHolder holder, View view, String title);
}
