package com.tobin.recipe.linkage.defaults;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tobin.recipe.R;
import com.tobin.recipe.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.tobin.recipe.linkage.contract.ILinkagePrimaryAdapterConfig;

public class DefaultLinkagePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemBindListener mListener;
    private OnPrimaryItemClickListener mClickListener;

    public void setListener(OnPrimaryItemBindListener listener,
                            OnPrimaryItemClickListener clickListener) {
        mListener = listener;
        mClickListener = clickListener;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_adapter_linkage_primary;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title);

        tvTitle.setBackgroundColor(mContext.getResources().getColor(selected
                ? android.R.color.holo_blue_dark : android.R.color.white));
        tvTitle.setTextColor(ContextCompat.getColor(mContext, selected
                ? android.R.color.white : android.R.color.darker_gray));
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        tvTitle.setFocusable(selected);
        tvTitle.setFocusableInTouchMode(selected);
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);

        if (mListener != null) {
            mListener.onBindViewHolder(holder, title);
        }
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
        if (mClickListener != null) {
            mClickListener.onItemClick(holder, view, title);
        }
    }

    public interface OnPrimaryItemClickListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param holder primaryHolder
         * @param view   view
         * @param title  groupTitle
         */
        void onItemClick(LinkagePrimaryViewHolder holder, View view, String title);
    }

    public interface OnPrimaryItemBindListener {
        /**
         * Note: Please do not override rootView click listener in here, because of linkage selection rely on it.
         * and we suggest you get position by holder.getAdapterPosition
         *
         * @param primaryHolder primaryHolder
         * @param title         groupTitle
         */
        void onBindViewHolder(LinkagePrimaryViewHolder primaryHolder, String title);
    }
}
