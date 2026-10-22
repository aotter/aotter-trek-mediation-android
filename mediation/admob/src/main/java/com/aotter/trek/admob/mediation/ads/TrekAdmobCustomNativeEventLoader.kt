package com.aotter.trek.admob.mediation.ads

import android.content.Context
import android.util.Log
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.TrekLogLevel
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.trek.admob.mediation.BuildConfig
import com.aotter.trek.admob.mediation.extension.logAt
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationNativeAdCallback
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper


class TrekAdmobCustomNativeEventLoader(
    private val context: Context,
    private val mediationAdLoadCallback: MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
) : TrekAdListener {

    private val TAG: String = TrekAdmobCustomNativeEventLoader::class.java.simpleName

    private val SDK_DOMAIN = BuildConfig.LIBRARY_PACKAGE_NAME

    private var mediationNativeAdCallback: MediationNativeAdCallback? = null

    override fun onAdFailedToLoad(message: String) {

        mediationAdLoadCallback.onFailure(AdError(0, message, SDK_DOMAIN))

        Log.e(TAG, "AdError: $message")

    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {

        val trekAdmobUnifiedNativeAdMapper = TrekAdmobUnifiedNativeAdMapper(context)

        trekAdmobUnifiedNativeAdMapper.mappingNativeData(trekNativeAd)

        mediationNativeAdCallback =
            mediationAdLoadCallback.onSuccess(trekAdmobUnifiedNativeAdMapper)

        logAt(TrekLogLevel.NORMAL, TAG, "AdLoaded success.")

    }

    override fun onAdClicked() {

        logAt(TrekLogLevel.NORMAL, TAG, "AdClicked success.")

        mediationNativeAdCallback?.reportAdClicked()

    }

    override fun onAdImpression() {

        logAt(TrekLogLevel.NORMAL, TAG, "Native ad impression success.")

        mediationNativeAdCallback?.reportAdImpression()

    }

}