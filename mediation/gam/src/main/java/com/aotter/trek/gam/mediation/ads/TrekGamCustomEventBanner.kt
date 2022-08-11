package com.aotter.trek.gam.mediation.ads


import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.net.utils.TrekSdkSettingsUtils
import com.aotter.trek.gam.mediation.BuildConfig
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.aotter.trek.gam.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.*
import org.json.JSONObject
import java.util.concurrent.ConcurrentLinkedQueue


class TrekGamCustomEventBanner : Adapter() {

    private var TAG: String = TrekGamCustomEventBanner::class.java.simpleName


    companion object {
        private const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val PLACE_UID = "placeUid"
        private const val CLIENT_ID = "clientId"
        private var concurrentLinkedQueue = ConcurrentLinkedQueue<TrekBannerAdView>()
    }

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: MutableList<MediationConfiguration>
    ) {

        return

    }

    override fun loadBannerAd(
        mediationNativeAdConfiguration: MediationBannerAdConfiguration,
        mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
    ) {

        try {

            val serverParameterDto = JSONObject(
                mediationNativeAdConfiguration.serverParameters.getString(
                    SERVER_PARAMETER
                ) ?: ""
            )

            val clientId = serverParameterDto.getString(CLIENT_ID)

            val placeUid = serverParameterDto.getString(PLACE_UID)

            val context = mediationNativeAdConfiguration.context

            if (clientId.isEmpty()) {
                throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
            }

            if (placeUid.isNullOrEmpty()) {
                throw IllegalArgumentException(NEED_PLACE_UUID_TAG)
            }

            TrekAds.initialize(
                context,
                clientId
            ) {

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

                val oldTrekBannerAdView = concurrentLinkedQueue.poll()

                oldTrekBannerAdView?.destroy()

                TrekBannerAdView(context, null).apply {

                    val trekGamCustomBannerEventLoader = TrekGamCustomBannerEventLoader(this)

                    trekGamCustomBannerEventLoader.mediationAdLoadCallback =
                        mediationAdLoadCallback

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

                    this.setPlaceUid(placeUid)

                    this.loadAd(trekAdRequest)

                    concurrentLinkedQueue.offer(this)

                }

            }

        } catch (e: Exception) {

            throw Exception(e.toString())

        }

    }


    override fun getSDKVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

    override fun getVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

}