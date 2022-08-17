package com.aotter.trek.max.mediation.ads

import android.app.Activity
import android.util.Log
import com.aotter.net.trek.ads.TrekAdLoader
import com.aotter.net.trek.ads.TrekAdRequest
import com.aotter.net.trek.ads.ad_view_binding.TrekAdViewBinder
import com.aotter.trek.max.mediation.BuildConfig
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.adapter.parameters.MaxAdapterResponseParameters
import com.applovin.sdk.AppLovinSdk

class TrekMaxNativeAdapter(appLovinSdk: AppLovinSdk) : TrekMaxAdapterBase(appLovinSdk) {

    private var TAG: String = TrekMaxNativeAdapter::class.java.simpleName

    private var trekMaxNativeAdapterLoader: TrekMaxNativeAdapterLoader? = null

    override fun loadNativeAd(
        maxAdapterResponseParameters: MaxAdapterResponseParameters?,
        activity: Activity?,
        maxNativeAdAdapterListener: MaxNativeAdAdapterListener?
    ) {

        try {

            val trekParameters = getTrekParameters(maxAdapterResponseParameters)

            val placeUid = trekParameters.placeUid

            if (activity == null) {
                throw NullPointerException(NEED_CORRECT_CONTEXT)
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

            Log.i(TAG, "placeUid : $placeUid")

            Log.i(TAG, "category : $category")

            Log.i(TAG, "contentUrl : $contentUrl")

            Log.i(TAG, "contentTitle : $contentTitle")

            trekMaxNativeAdapterLoader = TrekMaxNativeAdapterLoader(
                activity,
                maxNativeAdAdapterListener
            )

            val trekAdLoader = TrekAdLoader
                .Builder(activity, trekParameters.placeUid)
                .withAdListener(
                    trekMaxNativeAdapterLoader
                )
                .build()

            val trekAdRequest = TrekAdRequest
                .Builder()
                .setCategory(trekParameters.category)
                .setContentUrl(trekParameters.contentUrl)
                .setContentTitle(trekParameters.contentTitle)
                .setMediationVersion(BuildConfig.MEDIATION_VERSION)
                .setMediationVersionCode(BuildConfig.MEDIATION_VERSION_CODE.toInt())
                .build()

            trekAdLoader.loadAd(trekAdRequest)


        } catch (e: Exception) {

            throw Exception(e.toString())

        }

    }

    override fun onDestroy() {

        trekMaxNativeAdapterLoader?.trekNativeAd?.apply {

            TrekAdViewBinder.destroy(this)

            Log.i(TAG, "NativeAd Destroy.")

        }

    }

}