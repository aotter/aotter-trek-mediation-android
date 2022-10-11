package com.aotter.trek.gam.mediation.ads


import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.gam.mediation.BuildConfig
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.google.android.gms.ads.mediation.MediationAdLoadCallback
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.mediation.MediationBannerAdCallback
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration
import org.json.JSONObject


class TrekGamCustomEventBanner : TrekGamCustomEventBase() {

    private var TAG: String = TrekGamCustomEventBanner::class.java.simpleName

    override fun loadBannerAd(
        mediationNativeAdConfiguration: MediationBannerAdConfiguration,
        mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
    ) {

        try {

            val serverParameter = JSONObject(
                mediationNativeAdConfiguration.serverParameters.getString(
                    SERVER_PARAMETER
                ) ?: ""
            )

            val clientId = serverParameter.getString(CLIENT_ID)

            val placeUid = serverParameter.getString(PLACE_UID)

            val context = mediationNativeAdConfiguration.context

            if (clientId.isNullOrEmpty()) {
                throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
            }

            if (placeUid.isNullOrEmpty()) {
                throw IllegalArgumentException(NEED_PLACE_UUID_TAG)
            }

            val category =
                mediationNativeAdConfiguration.mediationExtras.getString(TrekGamDataKey.CATEGORY)
                    ?: ""

            val contentUrl =
                mediationNativeAdConfiguration.mediationExtras.getString(TrekGamDataKey.CONTENT_URL)
                    ?: ""

            val contentTitle =
                mediationNativeAdConfiguration.mediationExtras.getString(TrekGamDataKey.CONTENT_TITLE)
                    ?: ""

            Log.i(TAG, "clientId : $clientId")

            Log.i(TAG, "placeUid : $placeUid")

            Log.i(TAG, "category : $category")

            Log.i(TAG, "contentUrl : $contentUrl")

            Log.i(TAG, "contentTitle : $contentTitle")

            TrekAds.initialize(
                context,
                clientId,
                object : TrekAds.OnInitializationCompleteListener {
                    override fun onInitializationComplete() {

                        TrekBannerAdView(context, null).apply {

                            val trekGamCustomBannerEventLoader =
                                TrekGamCustomBannerEventLoader(this, mediationAdLoadCallback)

                            this.setAdListener(
                                trekGamCustomBannerEventLoader
                            )

                            val trekAdRequest = TrekAdRequest
                                .Builder()
                                .setCategory(category)
                                .setContentUrl(contentUrl)
                                .setContentTitle(contentTitle)
                                .setMediationVersion(BuildConfig.MEDIATION_VERSION)
                                .setMediationVersionCode(BuildConfig.MEDIATION_VERSION_CODE.toInt())
                                .build()

                            this.preload = false

                            this.autoRefresh = false

                            this.setPlaceUid(placeUid)

                            this.loadAd(trekAdRequest)

                        }

                    }
                }
            )

        } catch (e: Exception) {

            throw Exception(e.toString())

        }

    }

}