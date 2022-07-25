package com.aotter.trek.max.mediation.demo

import android.content.Context
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder

object MaxAdViewCreator {

    fun createNativeAdView2(context: Context): MaxNativeAdView {
        val binder: MaxNativeAdViewBinder =
            MaxNativeAdViewBinder.Builder(R.layout.item_max_native_ad)
                .setTitleTextViewId(R.id.adTitle)
                .setIconImageViewId(R.id.adImg)
                .setAdvertiserTextViewId(R.id.advertiser)
                .build()
        return MaxNativeAdView(binder, context)
    }

    fun createNativeAdView(context: Context): MaxNativeAdView {
        val binder: MaxNativeAdViewBinder =
            MaxNativeAdViewBinder.Builder(R.layout.item_max_native_ad)
                .setTitleTextViewId(R.id.adTitle)
                .setIconImageViewId(R.id.adImg)
                .setAdvertiserTextViewId(R.id.advertiser)
                .setMediaContentViewGroupId(R.id.mediaViewContainer)
                .build()
        return MaxNativeAdView(binder, context)
    }


}