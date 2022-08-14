package com.aotter.trek.gam.mediation.ads


import android.content.Context
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.ads.TrekAdLoader
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.trek.gam.mediation.BuildConfig
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.aotter.trek.gam.mediation.extension.getVersion
import com.google.android.gms.ads.mediation.*
import org.json.JSONObject

class TrekGamCustomEventNative : Adapter() {

    private var TAG: String = TrekGamCustomEventNative::class.java.simpleName

    companion object {
        private const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        private const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
        private const val SERVER_PARAMETER = "parameter"
        private const val PLACE_UID = "placeUid"
        private const val CLIENT_ID = "clientId"
        private const val CLASS_NAME = "class_name"
    }

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: MutableList<MediationConfiguration>
    ) {

        try {

            mediationConfigurations.forEach { mediationConfiguration ->

                val className = TrekGamCustomEventNative::class.java.name

                val serverParameters = mediationConfiguration.serverParameters

                if (serverParameters.getString(CLASS_NAME) == className) {

                    val serverParameter = JSONObject(
                        mediationConfiguration.serverParameters.getString(
                            SERVER_PARAMETER
                        ) ?: ""
                    )

                    val clientId = serverParameter.getString(CLIENT_ID)

                    if (clientId.isNullOrEmpty()) {
                        throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
                    }

                    Log.i(TAG, "clientId : $clientId")

                    TrekAds.initialize(
                        context,
                        clientId
                    ) {
                        initializationCompleteCallback.onInitializationSucceeded()
                    }

                }

            }

        } catch (e: Exception) {

            initializationCompleteCallback.onInitializationFailed(e.toString())

        }

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

    override fun getSDKVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

    override fun getVersionInfo(): VersionInfo {

        return BuildConfig.MEDIATION_VERSION.getVersion()

    }

}