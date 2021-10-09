package com.tobin.recipe.linkage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tobin.recipe.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.tobin.recipe.linkage.contract.ILinkagePrimaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

public class LinkagePrimaryAdapter extends RecyclerView.Adapter<LinkagePrimaryViewHolder> {

    private List<String> mStrings;
    private int mSelectedPosition;

    private final ILinkagePrimaryAdapterConfig mConfig;
    private final OnLinkageListener mLinkageListener;

    public List<String> getStrings() {
        return mStrings;
    }

    public ILinkagePrimaryAdapterConfig getConfig() {
        return mConfig;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public LinkagePrimaryAdapter(List<String> strings, ILinkagePrimaryAdapterConfig config,
                                 OnLinkageListener linkageListener) {
        mStrings = strings;
        if (mStrings == null) {
            mStrings = new ArrayList<>();
        }
        mConfig = config;
        mLinkageListener = linkageListener;
    }

    public void initData(List<String> list) {
        mStrings.clear();
        if (list != null) {
            mStrings.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinkagePrimaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        mConfig.setContext(mContext);
        View mView = LayoutInflater.from(mContext).inflate(mConfig.getLayoutId(), parent, false);
        return new LinkagePrimaryViewHolder(mView, mConfig);
    }

    @Override
    public void onBindViewHolder(@NonNull final LinkagePrimaryViewHolder holder, int position) {

        // for textView MARQUEE available.
        holder.mLayout.setSelected(true);

        final int adapterPosition = holder.getBindingAdapterPosition();
        final String title = mStrings.get(adapterPosition);

        mConfig.onBindViewHolder(holder, adapterPosition == mSelectedPosition, title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinkageListener != null) {
                    mLinkageListener.onLinkageClick(holder, title);
                }
                mConfig.onItemClick(holder, v, title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    /**
     * only for linkage logic of level primary adapter. not use for outside logic
     * users can archive onLinkageClick in configs instead.
     */
    public interface OnLinkageListener {
        void onLinkageClick(LinkagePrimaryViewHolder holder, String title);
    }
}
