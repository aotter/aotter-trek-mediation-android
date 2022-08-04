package com.aotter.trek.admob.mediation.ads


import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.admob.mediation.BuildConfig
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.*
import org.json.JSONObject


class TrekAdmobCustomEventBanner : Adapter() {

    private var TAG: String = TrekAdmobCustomEventBanner::class.java.simpleName


    companion object {
        private const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val PLACE_UID = "placeUid"
        private const val CLIENT_ID = "clientId"
        private var trekBannerAdView: TrekBannerAdView? = null
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
                    mediationNativeAdConfiguration.mediationExtras.getString(TrekAdmobDataKey.CATEGORY)
                        ?: ""

                val contentUrl =
                    mediationNativeAdConfiguration.mediationExtras.getString(TrekAdmobDataKey.CONTENT_URL)
                        ?: ""

                val contentTitle =
                    mediationNativeAdConfiguration.mediationExtras.getString(TrekAdmobDataKey.CONTENT_TITLE)
                        ?: ""

                Log.i(TAG, "clientId : $clientId")

                Log.i(TAG, "placeUid : $placeUid")

                Log.i(TAG, "category : $category")

                Log.i(TAG, "contentUrl : $contentUrl")

                Log.i(TAG, "contentTitle : $contentTitle")


                if (trekBannerAdView == null || trekBannerAdView?.childCount == 0) {

                    trekBannerAdView = TrekBannerAdView(context, null)

                }

                trekBannerAdView?.apply {

                    this.setAdListener(
                        TrekAdmobCustomBannerEventLoader(
                            this,
                            mediationAdLoadCallback
                        )
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