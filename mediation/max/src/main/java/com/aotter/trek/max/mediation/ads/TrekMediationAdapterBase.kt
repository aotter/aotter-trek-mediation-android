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

abstract class TrekMediationAdapterBase(appLovinSdk: AppLovinSdk) :
    MediationAdapterBase(appLovinSdk) {

    private var TAG: String = TrekMediationAdapterBase::class.java.simpleName

    private var scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    companion object {
        private const val CLIENT_ID = "clientId"
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

            val clientId =
                maxAdapterInitializationParameters?.customParameters?.getString(CLIENT_ID) ?: ""

            if (activity == null) {
                throw NullPointerException(NEED_CORRECT_CONTEXT)
            }

            TrekAds.initialize(activity,clientId) {

                onCompletionListener?.onCompletion(
                    MaxAdapter.InitializationStatus.INITIALIZED_SUCCESS,
                    null
                )

                Log.i(TAG, "TrekAds initialize success.")

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

        var clientId = ""

        var placeUid = ""

        var category = ""

        var contentUrl = ""

        var contentTitle = ""

        maxAdapterResponseParameters?.apply {

            clientId = this.customParameters.getString(CLIENT_ID) ?: ""

            placeUid = this.thirdPartyAdPlacementId ?: ""

            category = (this.localExtraParameters[TrekMaxDataKey.CATEGORY] as? String) ?: ""

            contentUrl = (this.localExtraParameters[TrekMaxDataKey.CONTENT_URL] as? String) ?: ""

            contentTitle =
                (this.localExtraParameters[TrekMaxDataKey.CONTENT_TITLE] as? String) ?: ""

        }

        return TrekParameters(clientId, placeUid, category, contentUrl, contentTitle)

    }

}