package com.tobin.recipe.linkage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tobin.recipe.R;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.tobin.recipe.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.tobin.recipe.linkage.bean.BaseGroupedItem;
import com.tobin.recipe.linkage.contract.ILinkageSecondaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

public class LinkageSecondaryAdapter<T extends BaseGroupedItem.ItemInfo>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseGroupedItem<T>> mItems;
    private static final int IS_HEADER = 0;
    private static final int IS_LINEAR = 1;
    private static final int IS_GRID = 2;
    private static final int IS_FOOTER = 3;
    private boolean mIsGridMode;

    private final ILinkageSecondaryAdapterConfig<T> mConfig;

    public ILinkageSecondaryAdapterConfig<T> getConfig() {
        return mConfig;
    }

    public List<BaseGroupedItem<T>> getItems() {
        return mItems;
    }

    public boolean isGridMode() {
        return mIsGridMode && mConfig.getGridLayoutId() != 0;
    }

    public void setGridMode(boolean isGridMode) {
        mIsGridMode = isGridMode;
    }

    public LinkageSecondaryAdapter(List<BaseGroupedItem<T>> items, ILinkageSecondaryAdapterConfig<T> config) {
        mItems = items;
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mConfig = config;
    }

    public void initData(List<BaseGroupedItem<T>> list) {
        mItems.clear();
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).isHeader) {
            return IS_HEADER;
        } else if (TextUtils.isEmpty(mItems.get(position).info.getContent()) &&
                !TextUtils.isEmpty(mItems.get(position).info.getGroup())) {
            return IS_FOOTER;
        } else if (isGridMode()) {
            return IS_GRID;
        } else {
            return IS_LINEAR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        mConfig.setContext(mContext);
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getHeaderLayoutId(), parent, false);
            return new LinkageSecondaryHeaderViewHolder(view);
        } else if (viewType == IS_FOOTER) {
            int footerLayout = mConfig.getFooterLayoutId() == 0
                    ? R.layout.recipe_adapter_linkage_secondary_footer
                    : mConfig.getFooterLayoutId();
            View view = LayoutInflater.from(mContext).inflate(footerLayout, parent, false);
            return new LinkageSecondaryFooterViewHolder(view);
        } else if (viewType == IS_GRID && mConfig.getGridLayoutId() != 0) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getGridLayoutId(), parent, false);
            return new LinkageSecondaryViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getLinearLayoutId(), parent, false);
            return new LinkageSecondaryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BaseGroupedItem<T> linkageItem = mItems.get(holder.getBindingAdapterPosition());
        if (getItemViewType(holder.getBindingAdapterPosition()) == IS_HEADER) {
            LinkageSecondaryHeaderViewHolder headerViewHolder = (LinkageSecondaryHeaderViewHolder) holder;
            mConfig.onBindHeaderViewHolder(headerViewHolder, linkageItem);
        } else if (getItemViewType(holder.getBindingAdapterPosition()) == IS_FOOTER) {
            LinkageSecondaryFooterViewHolder footerViewHolder = (LinkageSecondaryFooterViewHolder) holder;
            mConfig.onBindFooterViewHolder(footerViewHolder, linkageItem);
        } else {
            LinkageSecondaryViewHolder secondaryViewHolder = (LinkageSecondaryViewHolder) holder;
            mConfig.onBindViewHolder(secondaryViewHolder, linkageItem);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
