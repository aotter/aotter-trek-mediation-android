package com.aotter.trek.max.mediation.ads

import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.TrekLogLevel
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.max.mediation.extension.logAt
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener


class TrekMaxBannerAdapterLoader(
    private val trekBannerAdView: TrekBannerAdView,
    private val maxAdViewAdapterListener: MaxAdViewAdapterListener?
) : TrekAdListener {

    private var TAG: String = TrekMaxBannerAdapterLoader::class.java.simpleName

    override fun onAdFailedToLoad(message: String) {

        maxAdViewAdapterListener?.onAdViewAdLoadFailed(MaxAdapterError.NO_FILL)

        logAt(TrekLogLevel.NORMAL, TAG, "Ad failed to load.")

    }


    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        maxAdViewAdapterListener?.onAdViewAdLoaded(trekBannerAdView)

        logAt(TrekLogLevel.NORMAL, TAG, "AdLoaded success.")

    }

    override fun onAdClicked() {
        super.onAdClicked()

        maxAdViewAdapterListener?.onAdViewAdClicked()

        logAt(TrekLogLevel.NORMAL, TAG, "Ad Clicked.")

    }

    override fun onAdImpression() {
        super.onAdImpression()

        maxAdViewAdapterListener?.onAdViewAdDisplayed()

        logAt(TrekLogLevel.NORMAL, TAG, "Ad impression.")

    }

}