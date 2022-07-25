package com.aotter.trek.gam.mediation.demo

import androidx.recyclerview.widget.DiffUtil

class ItemCallback : DiffUtil.ItemCallback<GamLocalNativeAdData>() {

    override fun areItemsTheSame(oldItem: GamLocalNativeAdData, newItem: GamLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: GamLocalNativeAdData, newItem: GamLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

}