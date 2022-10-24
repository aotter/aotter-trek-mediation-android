package com.aotter.trek.max.mediation.ads

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekAdListener
import com.aotter.net.trek.ads.TrekAdViewBinder
import com.aotter.net.trek.ads.TrekMediaView
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.adapter.MaxAdapterError
import com.applovin.mediation.adapter.listeners.MaxNativeAdAdapterListener
import com.applovin.mediation.nativeAds.MaxNativeAd
import com.applovin.mediation.nativeAds.MaxNativeAdView


class TrekMaxNativeAdapterLoader(
    private val context: Context,
    private val maxNativeAdAdapterListener: MaxNativeAdAdapterListener?
) : TrekAdListener {

    private var TAG: String = TrekMaxNativeAdapterLoader::class.java.simpleName

    var trekNativeAd: TrekNativeAd? = null

    var trekAdViewBinder: TrekAdViewBinder? = null

    private val trekMediaView by lazy {

        TrekMediaView(context, null)

    }

    override fun onAdLoaded(trekNativeAd: TrekNativeAd) {
        super.onAdLoaded(trekNativeAd)

        this@TrekMaxNativeAdapterLoader.trekNativeAd = trekNativeAd

        val imgIconHd = trekNativeAd.imgIconHd.drawable?.let { drawable ->
            MaxNativeAd.MaxNativeAdImage(drawable)
        } ?: kotlin.run {
            MaxNativeAd.MaxNativeAdImage(trekNativeAd.imgIconHd.uri ?: Uri.parse(""))
        }

        val imgMain = trekNativeAd.imgMain.drawable?.let { drawable ->
            MaxNativeAd.MaxNativeAdImage(drawable)
        } ?: kotlin.run {
            MaxNativeAd.MaxNativeAdImage(trekNativeAd.imgMain.uri ?: Uri.parse(""))
        }

        val builder = MaxNativeAd.Builder()
            .setAdFormat(MaxAdFormat.NATIVE)
            .setIcon(imgIconHd)
            .setMainImage(imgMain)
            .setMediaView(trekMediaView)
            .setTitle(trekNativeAd.title ?: "")
            .setBody(trekNativeAd.text ?: "")
            .setAdvertiser(trekNativeAd.advertiserName ?: "")
            .setCallToAction(trekNativeAd.callToAction ?: "")


        val trekMaxNativeAd = TrekMaxNativeAd(builder, trekNativeAd)

        maxNativeAdAdapterListener?.onNativeAdLoaded(trekMaxNativeAd, getBundle(trekNativeAd))

        Log.i(TAG, "Ad Loaded.")

    }

    override fun onAdClicked() {
        super.onAdClicked()

        maxNativeAdAdapterListener?.onNativeAdClicked()

        Log.i(TAG, "Ad Clicked.")

    }

    override fun onAdFailedToLoad(message: String) {
        super.onAdFailedToLoad(message)

        maxNativeAdAdapterListener?.onNativeAdLoadFailed(MaxAdapterError.NO_FILL)

        Log.i(TAG, "Ad failed to load.")

    }

    override fun onAdImpression() {
        super.onAdImpression()

        maxNativeAdAdapterListener?.onNativeAdDisplayed(getBundle(trekNativeAd))

        Log.i(TAG, "Ad impression.")

    }

    private fun getBundle(trekNativeAd: TrekNativeAd?): Bundle {

        val bundle = Bundle()

        trekNativeAd?.apply {

            bundle.putString(TrekMaxDataKey.SPONSOR, this.sponsor)

            bundle.putString(TrekMaxDataKey.MAIN_IMAGE, this.imgMain.uri.toString())

            bundle.putString(TrekMaxDataKey.ICON_IMAGE, this.imgIcon.uri.toString())

            bundle.putString(TrekMaxDataKey.ICON_IMAGE_HD, this.imgIconHd.uri.toString())

        }

        return bundle

    }

    inner class TrekMaxNativeAd(builder: Builder, private val trekNativeAd: TrekNativeAd) :
        MaxNativeAd(builder) {

        override fun prepareViewForInteraction(maxNativeAdView: MaxNativeAdView?) {

            maxNativeAdView?.let { maxNativeAdView ->

                val trekMediaView: TrekMediaView? =
                    maxNativeAdView.mediaContentViewGroup?.findViewWithTag(
                        trekMediaView.tag
                    )

                trekAdViewBinder =
                    TrekAdViewBinder(maxNativeAdView, trekMediaView, trekNativeAd).apply {

                        this.bindAdView()

                    }

                Log.i(TAG, "Prepare view for interaction finish.")

            }

        }

    }

}