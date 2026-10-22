package com.aotter.trek.admob.mediation.ads

import android.util.Log
import android.view.View
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.TrekLogLevel
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.admob.mediation.BuildConfig
import com.aotter.trek.admob.mediation.extension.logAt
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback

class TrekAdmobCustomBannerEventLoader(
    private val trekBannerAdView: TrekBannerAdView,
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>?
) : TrekAdListener, MediationBannerAd {

    private val TAG: String = TrekAdmobCustomBannerEventLoader::class.java.simpleName

    private val SDK_DOMAIN = BuildConfig.LIBRARY_PACKAGE_NAME

    private var mediationBannerAdCallback: MediationBannerAdCallback? = null

    override fun onAdFailedToLoad(message: String) {

        mediationAdLoadCallback?.onFailure(AdError(0, message, SDK_DOMAIN))

        Log.e(TAG, "AdError: $message")
    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        mediationBannerAdCallback = mediationAdLoadCallback?.onSuccess(this)

        logAt(TrekLogLevel.NORMAL, TAG, "AdLoaded success.")

    }

    override fun onAdClicked() {

        logAt(TrekLogLevel.NORMAL, TAG, "AdClicked success.")

        mediationBannerAdCallback?.reportAdClicked()

    }

    override fun onAdImpression() {

        logAt(TrekLogLevel.NORMAL, TAG, "Banner Ad  impression success.")

        mediationBannerAdCallback?.reportAdImpression()

    }

    override fun getView(): View {

        return trekBannerAdView

    }


}