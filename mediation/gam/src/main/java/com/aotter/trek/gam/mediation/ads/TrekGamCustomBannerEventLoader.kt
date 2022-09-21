package com.aotter.trek.gam.mediation.ads

import android.util.Log
import android.view.View
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.gam.mediation.BuildConfig
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback

class TrekGamCustomBannerEventLoader(
    private val trekBannerAdView: TrekBannerAdView,
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) : TrekAdListener, MediationBannerAd {

    private val TAG: String = TrekGamCustomBannerEventLoader::class.java.simpleName

    private val SDK_DOMAIN = BuildConfig.LIBRARY_PACKAGE_NAME

    private var mediationBannerAdCallback: MediationBannerAdCallback? = null

    override fun onAdsFailedToLoad(messages: List<String>) {

        val message = if (messages.isEmpty()) {
            "Exception error."
        } else {
            messages[0]
        }

        mediationAdLoadCallback.onFailure(AdError(0, message, SDK_DOMAIN))

        Log.e(TAG, "AdError: ${message}")
    }

    override fun onAdsLoaded(trekNativeAds: List<TrekNativeAd>) {

        mediationBannerAdCallback = mediationAdLoadCallback.onSuccess(this)

        Log.i(TAG, "AdLoaded success.")

    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        mediationBannerAdCallback = mediationAdLoadCallback.onSuccess(this)

        Log.i(TAG, "AdLoaded success.")

    }

    override fun onAdClicked() {

        Log.i(TAG, "AdClicked success.")

        mediationBannerAdCallback?.reportAdClicked()

    }

    override fun onAdImpression() {

        Log.i(TAG, "Banner Ad  impression success.")

        mediationBannerAdCallback?.reportAdImpression()

    }

    override fun getView(): View {

        return trekBannerAdView

    }


}