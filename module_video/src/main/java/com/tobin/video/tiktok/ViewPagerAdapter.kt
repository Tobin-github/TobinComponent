package com.tobin.video.tiktok

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tobin.video.databinding.VideoTiktokPagerItemBinding

class ViewPagerAdapter(
    private val context: Context,
    private val itemDataList: List<ShortVideoBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = VideoTiktokPagerItemBinding.inflate( LayoutInflater.from(context), parent, false)
        return RecyclerItemNormalHolder(context, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItemViewHolder = holder as RecyclerItemNormalHolder
        recyclerItemViewHolder.recyclerBaseAdapter = this
        recyclerItemViewHolder.onBind(position, itemDataList[position])
    }

    override fun getItemCount(): Int {
        return itemDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    companion object {
        private const val TAG = "ViewPagerAdapter"
    }
}