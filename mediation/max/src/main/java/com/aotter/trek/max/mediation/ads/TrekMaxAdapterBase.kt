package com.aotter.trek.max.mediation.ads

import android.app.Activity
import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.trek.max.mediation.BuildConfig
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.aotter.trek.max.mediation.TrekParameters
import com.applovin.mediation.adapter.MaxAdapter
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.mediation.adapters.MediationAdapterBase
import com.applovin.sdk.AppLovinSdk

abstract class TrekMaxAdapterBase(appLovinSdk: AppLovinSdk) :
    MediationAdapterBase(appLovinSdk) {

    private var TAG: String = TrekMaxAdapterBase::class.java.simpleName

    companion object {
         const val APP_ID = "app_id"
         const val ADAPTER_CLASS = "adapter_class"
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

            clientId = this.serverParameters?.getString(APP_ID) ?: ""

            placeUid = this.thirdPartyAdPlacementId ?: ""

            category = (this.localExtraParameters[TrekMaxDataKey.CATEGORY] as? String) ?: ""

            contentUrl = (this.localExtraParameters[TrekMaxDataKey.CONTENT_URL] as? String) ?: ""

            contentTitle =
                (this.localExtraParameters[TrekMaxDataKey.CONTENT_TITLE] as? String) ?: ""

        }

        return TrekParameters(clientId,placeUid, category, contentUrl, contentTitle)

    }

}