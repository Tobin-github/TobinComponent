package com.tobin.recipe.linkage.contract;

import android.content.Context;

import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.tobin.recipe.linkage.bean.BaseGroupedItem;

public interface ILinkageSecondaryAdapterConfig<T extends BaseGroupedItem.ItemInfo> {

    /**
     * setContext
     *
     * @param context context
     */
    void setContext(Context context);

    /**
     * get grid layout res id
     *
     * @return grid layout res id
     */
    int getGridLayoutId();

    /**
     * get linear layout res id
     *
     * @return linear layout res id
     */
    int getLinearLayoutId();

    /**
     * get header layout res id
     * <p>
     * Note: Secondary adapter's Header and HeaderView must share the same set of views
     *
     * @return header layout res id
     */
    int getHeaderLayoutId();

    /**
     * get footer layout res id
     * <p>
     * Note: Footer is to avoid the extreme situation that
     * 'last group has too little items to sticky to avoid another issue'.
     *
     * @return footer layout res id
     */
    int getFooterLayoutId();

    /**
     * get the id of textView for bind title of HeaderView
     * <p>
     * Note: Secondary adapter's Header and HeaderView must share the same set of views
     *
     * @return int
     */
    int getHeaderTextViewId();

    /**
     * get SpanCount of grid mode
     */
    int getSpanCountOfGridMode();

    /**
     * achieve the onBindViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryViewHolder
     * @param item     linkageItem of this position
     */
    void onBindViewHolder(LinkageSecondaryViewHolder holder, BaseGroupedItem<T> item);

    /**
     * achieve the onBindHeaderViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryHeaderViewHolder
     * @param item     header of this position
     */
    void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<T> item);

    /**
     * achieve the onBindFooterViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryFooterViewHolder
     * @param item     footer of this position
     */
    void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder, BaseGroupedItem<T> item);
}
