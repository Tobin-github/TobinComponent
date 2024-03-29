package com.tobin.lib_resource.mvvm.binding_recyclerview.binding_adapter;

import android.annotation.SuppressLint;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import timber.log.Timber;

public class RecyclerViewBindingAdapter {

    @BindingAdapter(value = {"setAdapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"submitList"}, requireAll = false)
    public static void submitList(RecyclerView recyclerView, List<?> list) {
        if (recyclerView.getAdapter() != null) {
            Timber.d("submitList");
            ListAdapter adapter = (ListAdapter) recyclerView.getAdapter();
            adapter.submitList(list);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @BindingAdapter(value = {"notifyCurrentListChanged"}, requireAll = false)
    public static void notifyCurrentListChanged(RecyclerView recyclerView, boolean notifyCurrentListChanged) {
        if (notifyCurrentListChanged && recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @BindingAdapter(value = {"autoScrollToTopWhenInsert", "autoScrollToBottomWhenInsert"}, requireAll = false)
    public static void autoScroll(RecyclerView recyclerView,
                                  boolean autoScrollToTopWhenInsert,
                                  boolean autoScrollToBottomWhenInsert) {
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    if (autoScrollToTopWhenInsert) {
                        recyclerView.scrollToPosition(0);
                    } else if (autoScrollToBottomWhenInsert) {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
                    }
                }
            });
        }
    }


}
