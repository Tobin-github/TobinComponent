package com.tobin.recipe.linkage.adapter.viewholder;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private View mConvertView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mHeaderViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mHeaderViews.put(viewId, view);
        }
        return (T) view;
    }
}
