package com.aotter.trek.max.mediation.ads

import android.app.Activity
import android.util.Log
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.net.trek.ads.TrekBannerAdView
import com.aotter.trek.max.mediation.BuildConfig
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.adapter.MaxAdViewAdapter
import com.applovin.mediation.adapter.listeners.MaxAdViewAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.sdk.AppLovinSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TrekMaxBannerAdapter(appLovinSdk: AppLovinSdk) : TrekMediationAdapterBase(appLovinSdk),
    MaxAdViewAdapter {

    private var TAG: String = TrekMaxBannerAdapter::class.java.simpleName

    private var scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    companion object {

        private var trekBannerAdView: TrekBannerAdView? = null

    }

    override fun loadAdViewAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        maxAdFormat: MaxAdFormat?,
        activity: Activity?,
        maxAdViewAdapterListener: MaxAdViewAdapterListener?
    ) {

        scope.launch {

            try {

                val trekParameters = getTrekParameters(maxAdapterResponseParameters)

                Log.i(TAG, "trekParameters : $trekParameters")

                val clientId = trekParameters.clientId

                val placeUid = trekParameters.placeUid

                if (activity == null) {
                    throw NullPointerException(NEED_CORRECT_CONTEXT)
                }

                if (clientId.isEmpty()) {
                    throw IllegalArgumentException(NEED_CLIENT_ID_TAG)
                }

                if (placeUid.isEmpty()) {
                    throw IllegalArgumentException(NEED_PLACE_UUID_TAG)
                }

                val category =
                    trekParameters.category

                val contentUrl =
                    trekParameters.contentUrl

                val contentTitle =
                    trekParameters.contentTitle

                Log.i(TAG, "clientId : $clientId")

                Log.i(TAG, "placeUid : $placeUid")

                Log.i(TAG, "category : $category")

                Log.i(TAG, "contentUrl : $contentUrl")

                Log.i(TAG, "contentTitle : $contentTitle")

                if (trekBannerAdView == null || trekBannerAdView?.childCount == 0) {

                    trekBannerAdView = TrekBannerAdView(activity, null)

                }

                trekBannerAdView?.apply {

                    this.setAdListener(
                        TrekMaxBannerAdapterLoader(
                            this,
                            maxAdViewAdapterListener
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

            } catch (e: Exception) {

                throw Exception(e.toString())

            }

        }

    }


    override fun onDestroy() {

        // The lifecycle of TrekBannerAdView is automated.
        // TrekBannerAdView was destroyed when page close currently.

    }

}