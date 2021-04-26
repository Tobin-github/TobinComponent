package com.tobin.recipe.linkage.defaults;

import android.content.Context;
import android.widget.TextView;

import com.tobin.recipe.R;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.tobin.recipe.linkage.bean.BaseGroupedItem;
import com.tobin.recipe.linkage.contract.ILinkageSecondaryAdapterConfig;

public class DefaultLinkageSecondaryAdapterConfig implements ILinkageSecondaryAdapterConfig<BaseGroupedItem.ItemInfo> {

    private Context mContext;
    private OnSecondaryItemBindListener mItemBindListener;
    private OnSecondaryHeaderBindListener mHeaderBindListener;
    private OnSecondaryFooterBindListener mFooterBindListener;
    private static final int SPAN_COUNT = 3;

    public void setItemBindListener(OnSecondaryItemBindListener itemBindListener,
                                    OnSecondaryHeaderBindListener headerBindListener,
                                    OnSecondaryFooterBindListener footerBindListener) {
        mItemBindListener = itemBindListener;
        mHeaderBindListener = headerBindListener;
        mFooterBindListener = footerBindListener;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.recipe_adapter_linkage_secondary_grid;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.recipe_adapter_linkage_secondary_linear;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.recipe_adapter_linkage_secondary_header;
    }

    @Override
    public int getFooterLayoutId() {
        return R.layout.recipe_adapter_linkage_secondary_footer;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.secondary_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT;
    }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<BaseGroupedItem.ItemInfo> item) {

        ((TextView) holder.getView(R.id.level_2_content)).setText(item.info.getContent());
        if (mItemBindListener != null) {
            mItemBindListener.onBindViewHolder(holder, item);
        }
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<BaseGroupedItem.ItemInfo> item) {

        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);

        if (mHeaderBindListener != null) {
            mHeaderBindListener.onBindHeaderViewHolder(holder, item);
        }
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<BaseGroupedItem.ItemInfo> item) {
        ((TextView) holder.getView(R.id.tv_secondary_footer)).setText("End");
        if (mFooterBindListener != null) {
            mFooterBindListener.onBindFooterViewHolder(holder, item);
        }
    }

    public interface OnSecondaryItemBindListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param secondaryHolder LinkageSecondaryViewHolder
         * @param item BaseGroupedItem
         */
        void onBindViewHolder(LinkageSecondaryViewHolder secondaryHolder,
                              BaseGroupedItem<BaseGroupedItem.ItemInfo> item);
    }

    public interface OnSecondaryHeaderBindListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param headerHolder LinkageSecondaryHeaderViewHolder
         * @param item BaseGroupedItem
         */
        void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder headerHolder,
                                    BaseGroupedItem<BaseGroupedItem.ItemInfo> item);
    }

    public interface OnSecondaryFooterBindListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param footerHolder LinkageSecondaryFooterViewHolder
         * @param item BaseGroupedItem
         */
        void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder footerHolder,
                                    BaseGroupedItem<BaseGroupedItem.ItemInfo> item);
    }
}
