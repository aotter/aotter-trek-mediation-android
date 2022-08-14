package com.aotter.max.mediation.demo

import androidx.recyclerview.widget.DiffUtil

class ItemCallback : DiffUtil.ItemCallback<MaxLocalNativeAdData>() {

    override fun areItemsTheSame(oldItem: MaxLocalNativeAdData, newItem: MaxLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: MaxLocalNativeAdData, newItem: MaxLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

}