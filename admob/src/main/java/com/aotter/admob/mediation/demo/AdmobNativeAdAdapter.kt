package com.aotter.admob.mediation.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AdmobNativeAdAdapter(
    diffCallBack: ItemCallback
) :
    ListAdapter<AdmobLocalNativeAdData, RecyclerView.ViewHolder>(diffCallBack) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).adView?.let {
            0
        } ?: kotlin.run {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_conteainer_native_ad, parent, false)

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

        private val adTitle = itemView.findViewById<TextView>(R.id.adBody)

        private val advertiser = itemView.findViewById<TextView>(R.id.advertiser)

        private val adImg = itemView.findViewById<ImageView>(R.id.adImg)

        fun bind(item: AdmobLocalNativeAdData) {

            advertiser.text = item.advertiser

            adTitle.text = item.title

            adImg.setImageResource(R.drawable.aotter_girl)

        }

    }



    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val container = itemView.findViewById<CardView>(R.id.container)

        fun bind(item: AdmobLocalNativeAdData) {

            (item.adView?.parent as? ViewGroup)?.removeAllViews()

            container.removeAllViews()

            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            item.adView?.layoutParams = lp

            container.addView(item.adView)

        }

    }


}