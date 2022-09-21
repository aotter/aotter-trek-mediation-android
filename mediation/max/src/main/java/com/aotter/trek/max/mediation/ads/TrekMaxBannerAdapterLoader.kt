package com.aotter.trek.max.mediation.ads

import android.util.Log
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekBannerAdView
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener


class TrekMaxBannerAdapterLoader(
    private val trekBannerAdView: TrekBannerAdView,
    private val maxAdViewAdapterListener: MaxAdViewAdapterListener?
) : TrekAdListener {

    private var TAG: String = TrekMaxBannerAdapterLoader::class.java.simpleName

    override fun onAdsFailedToLoad(messages: List<String>) {

        maxAdViewAdapterListener?.onAdViewAdLoadFailed(MaxAdapterError.NO_FILL)

        Log.i(TAG, "Ad failed to load.")

    }

    override fun onAdFailedToLoad(message: String) {
        super.onAdFailedToLoad(message)

        maxAdViewAdapterListener?.onAdViewAdLoadFailed(MaxAdapterError.NO_FILL)

        Log.i(TAG, "Ad failed to load.")

    }

    override fun onAdsLoaded(trekNativeAds: List<TrekNativeAd>) {

        maxAdViewAdapterListener?.onAdViewAdLoaded(trekBannerAdView)

        Log.i(TAG, "AdLoaded success.")

    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        maxAdViewAdapterListener?.onAdViewAdLoaded(trekBannerAdView)

        Log.i(TAG, "Ad Loaded.")

    }


    override fun onAdClicked() {
        super.onAdClicked()

        maxAdViewAdapterListener?.onAdViewAdClicked()

        Log.i(TAG, "Ad Clicked.")

    }

    override fun onAdImpression() {
        super.onAdImpression()

        maxAdViewAdapterListener?.onAdViewAdDisplayed()

        Log.i(TAG, "Ad impression.")

    }

}