package com.aotter.trek.max.mediation.ads

import android.app.Activity
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.utils.TrekSdkSettingsUtils
import com.aotter.trek.max.mediation.BuildConfig
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.aotter.trek.max.mediation.TrekParameters
import com.applovin.mediation.adapter.MaxAdapter
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.mediation.adapters.MediationAdapterBase
import com.applovin.sdk.AppLovinSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject

abstract class TrekMediationAdapterBase(appLovinSdk: AppLovinSdk) :
    MediationAdapterBase(appLovinSdk) {

    private var TAG: String = TrekMediationAdapterBase::class.java.simpleName

    private var scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val adapters =
        listOf(TrekMaxBannerAdapter::class.java.name, TrekMaxNativeAdapter::class.java.name)

    companion object {
        private const val APP_ID = "app_id"
        private const val ADAPTER_CLASS = "adapter_class"
        const val NEED_CORRECT_CONTEXT =
            "Require a more restrictive Context that is of type Activity or Fragment. / Context not null. "
        const val NEED_PLACE_UUID_TAG = "Not found placeUid or empty string."
        const val NEED_CLIENT_ID_TAG = "Not found client id or empty string."
    }

    override fun initialize(
        maxAdapterInitializationParameters: MaxAdapterInitializationParameters?,
        activity: Activity?,
        onCompletionListener: MaxAdapter.OnCompletionListener?
    ) {

        scope.launch {

            try {

                val adapterClass =
                    maxAdapterInitializationParameters?.serverParameters?.getString(ADAPTER_CLASS) ?: ""

                if (adapterClass in adapters) {

                    val clientId =
                        maxAdapterInitializationParameters?.serverParameters?.getString(APP_ID) ?: ""

                    if (clientId.isEmpty()) {
                        throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
                    }

                    Log.i(TAG, "clientId : $clientId")

                    TrekAds.initialize(applicationContext, clientId) {

                        onCompletionListener?.onCompletion(
                            MaxAdapter.InitializationStatus.INITIALIZED_SUCCESS,
                            "TrekAds initialize success."
                        )

                        Log.i(TAG, "TrekAds initialize success.")

                    }

                }

            }catch (e:Exception){

                onCompletionListener?.onCompletion(
                    MaxAdapter.InitializationStatus.INITIALIZED_FAILURE,
                    e.toString()
                )

            }

        }

    }

    override fun getSdkVersion(): String {

        Log.i(TAG, "SdkVersion : ${TrekAds.getSdkVersion()}")

        return TrekAds.getSdkVersion()

    }

    override fun getAdapterVersion(): String {

        Log.i(TAG, "AdapterVersion : ${BuildConfig.MEDIATION_VERSION}")

        return BuildConfig.MEDIATION_VERSION

    }


    fun getTrekParameters(maxAdapterResponseParameters: MaxAdapterResponseParameters?): TrekParameters {

        var placeUid = ""

        var category = ""

        var contentUrl = ""

        var contentTitle = ""

        maxAdapterResponseParameters?.apply {

            placeUid = this.thirdPartyAdPlacementId ?: ""

            category = (this.localExtraParameters[TrekMaxDataKey.CATEGORY] as? String) ?: ""

            contentUrl = (this.localExtraParameters[TrekMaxDataKey.CONTENT_URL] as? String) ?: ""

            contentTitle =
                (this.localExtraParameters[TrekMaxDataKey.CONTENT_TITLE] as? String) ?: ""

        }

        return TrekParameters(placeUid, category, contentUrl, contentTitle)

    }

}