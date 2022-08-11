package com.aotter.trek.admob.mediation.ads

import android.util.Log
import android.view.View
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.admob.mediation.BuildConfig
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback

class TrekAdmobCustomBannerEventLoader(
    private val trekBannerAdView: TrekBannerAdView,
) : TrekAdListener, MediationBannerAd {

    private val TAG: String = TrekAdmobCustomBannerEventLoader::class.java.simpleName

    private val SDK_DOMAIN = BuildConfig.LIBRARY_PACKAGE_NAME

    private var mediationBannerAdCallback: MediationBannerAdCallback? = null

    var mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>? =
        null
        set(value) {
            field = value
        }

    override fun onAdFailedToLoad(message: String) {

        mediationAdLoadCallback?.onFailure(AdError(0, message, SDK_DOMAIN))

        Log.e(TAG, "AdError: $message")
    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        mediationBannerAdCallback = mediationAdLoadCallback?.onSuccess(this)

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