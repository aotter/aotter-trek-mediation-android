package com.aotter.gam.mediation.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView

class GamNativeAdAdapter(
    diffCallBack: ItemCallback
) :
    ListAdapter<GamLocalNativeAdData, RecyclerView.ViewHolder>(diffCallBack) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).nativeAd?.let {
            0
        } ?: kotlin.run {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_gam_native_ad, parent, false)

                AdViewHolder(view)

            }
            1 -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_local_ad, parent, false)
            )
            else -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_local_ad, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AdViewHolder) {
            holder.bind(getItem(position))
        }

        if (holder is ViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val adTitle = itemView.findViewById<TextView>(R.id.adTitle)

        private val advertiser = itemView.findViewById<TextView>(R.id.advertiser)

        private val adImg = itemView.findViewById<ImageView>(R.id.adImg)

        fun bind(item: GamLocalNativeAdData) {

            advertiser.text = item.advertiser

            adTitle.text = item.title

            adImg.setImageResource(R.drawable.aotter_girl)


        }

    }



    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val adTitle = itemView.findViewById<TextView>(R.id.adTitle)

        private val advertiser = itemView.findViewById<TextView>(R.id.advertiser)

        private val adImg = itemView.findViewById<ImageView>(R.id.adImg)

        private val nativeAdView =
            itemView.findViewById<NativeAdView>(R.id.nativeAdView)

        private val mediaView =
            itemView.findViewById<MediaView>(R.id.mediaView)


        fun bind(item: GamLocalNativeAdData) {

            item.nativeAd?.let {nativeAd->

                advertiser.text = nativeAd.advertiser

                adTitle.text = nativeAd.headline

                Glide.with(itemView.context)
                    .load(nativeAd.icon?.uri ?: "")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(adImg)

                nativeAdView.advertiserView = advertiser

                nativeAdView.iconView = adImg

                nativeAdView.headlineView = adTitle

                mediaView.setImageScaleType(ImageView.ScaleType.FIT_XY)

                nativeAdView.mediaView = mediaView

                nativeAdView.setNativeAd(nativeAd)

            }

        }

    }


}