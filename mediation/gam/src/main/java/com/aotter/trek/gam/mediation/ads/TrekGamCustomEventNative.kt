package com.aotter.trek.gam.mediation.ads


import android.util.Log
import com.aotter.net.trek.ads.TrekAdLoader
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.trek.gam.mediation.BuildConfig
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.google.android.gms.ads.mediation.*
import org.json.JSONObject

class TrekGamCustomEventNative : TrekGamCustomEventBase() {

    private var TAG: String = TrekGamCustomEventNative::class.java.simpleName

    companion object {
        private const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val PLACE_UID = "placeUid"
    }

    override fun loadNativeAd(
        mediationNativeAdConfiguration: MediationNativeAdConfiguration,
        mediationAdLoadCallback: MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
    ) {

        try {

            val context = mediationNativeAdConfiguration.context

            val serverParameterDto = JSONObject(
                mediationNativeAdConfiguration.serverParameters.getString(
                    SERVER_PARAMETER
                ) ?: ""
            )

            val placeUid = serverParameterDto.getString(PLACE_UID)

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

            Log.i(TAG, "placeUid : $placeUid")

            Log.i(TAG, "category : $category")

            Log.i(TAG, "contentUrl : $contentUrl")

            Log.i(TAG, "contentTitle : $contentTitle")

            val trekAdLoader = TrekAdLoader
                .Builder(context, placeUid)
                .withAdListener(
                    TrekGamCustomNativeEventLoader(
                        context,
                        mediationAdLoadCallback
                    )
                )
                .build()

            val trekAdRequest = TrekAdRequest
                .Builder()
                .setCategory(category)
                .setContentUrl(contentUrl)
                .setContentTitle(contentTitle)
                .setMediationVersion(BuildConfig.MEDIATION_VERSION)
                .setMediationVersionCode(BuildConfig.MEDIATION_VERSION_CODE.toInt())
                .build()

            trekAdLoader.loadAd(trekAdRequest)


        } catch (e: Exception) {

            throw Exception(e.toString())

        }


    }

}