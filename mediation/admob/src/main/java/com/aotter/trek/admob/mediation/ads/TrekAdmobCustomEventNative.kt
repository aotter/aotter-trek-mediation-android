package com.aotter.trek.admob.mediation.ads


import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.ads.TrekAdLoader
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.trek.admob.mediation.BuildConfig
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.*
import org.json.JSONObject

class TrekAdmobCustomEventNative : Adapter() {

    private var TAG: String = TrekAdmobCustomEventNative::class.java.simpleName

    companion object {
        private const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val PLACE_UID = "placeUid"
        private const val CLIENT_ID = "clientId"
    }

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: MutableList<MediationConfiguration>
    ) {

        return

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

            val clientId = serverParameterDto.getString(CLIENT_ID)

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

                val trekAdLoader = TrekAdLoader
                    .Builder(context, placeUid)
                    .withAdListener(
                        TrekAdmobCustomNativeEventLoader(
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