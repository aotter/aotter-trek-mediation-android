package com.aotter.trek.admob.mediation.demo

import androidx.recyclerview.widget.DiffUtil

class ItemCallback : DiffUtil.ItemCallback<AdmobLocalNativeAdData>() {

    override fun areItemsTheSame(oldItem: AdmobLocalNativeAdData, newItem: AdmobLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: AdmobLocalNativeAdData, newItem: AdmobLocalNativeAdData): Boolean {
        return oldItem.postId == newItem.postId
    }

}