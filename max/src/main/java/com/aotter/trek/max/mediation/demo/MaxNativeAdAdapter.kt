package com.aotter.trek.max.mediation.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aotter.trek.max.mediation.demo.databinding.ItemMaxNativeAdBinding
import com.aotter.trek.max.mediation.demo.databinding.ViewMaxNativeAdContainerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MaxNativeAdAdapter(diffCallBack: ItemCallback) :
    ListAdapter<MaxLocalNativeAdData, RecyclerView.ViewHolder>(diffCallBack) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).maxNativeAdView?.let {
            0
        } ?: kotlin.run {
            1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> AdViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_max_native_ad_container, parent, false)
            )
            1 -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_max_native_ad, parent, false)
            )
            else -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_max_native_ad, parent, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(getItem(position))
        }

        if (holder is AdViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = ItemMaxNativeAdBinding.bind(itemView)

        fun bind(item: MaxLocalNativeAdData) {

            viewBinding.advertiser.text = item.advertiser

            viewBinding.adTitle.text = item.title

            viewBinding.adImg.setImageResource(R.drawable.ic_aotter)

        }

    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding = ViewMaxNativeAdContainerBinding.bind(itemView)

        fun bind(item: MaxLocalNativeAdData) {

            (item.maxNativeAdView?.parent as? ViewGroup)?.removeAllViews()

            viewBinding.container.removeAllViews()

            viewBinding.container.addView(item.maxNativeAdView)

        }

    }


}