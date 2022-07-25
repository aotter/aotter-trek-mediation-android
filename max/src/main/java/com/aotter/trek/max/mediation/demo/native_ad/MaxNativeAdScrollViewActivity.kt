package com.aotter.trek.max.mediation.demo.native_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.aotter.trek.max.mediation.demo.MaxAdViewCreator
import com.aotter.trek.max.mediation.demo.databinding.ActivityMaxNativeAdScrollViewBinding
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView

class MaxNativeAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMaxNativeAdScrollViewBinding

    private var nativeAdMap = hashMapOf<MaxNativeAdLoader, MaxAd?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMaxNativeAdScrollViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        viewBinding.refreshBtn.setOnClickListener {

            viewBinding.adContainer.removeAllViews()

            destroyAd()

            getAd(6)

        }

        getAd(6)

    }

    private fun getAd(count: Int) {

        val adUnitId: String

        val maxNativeAdView: MaxNativeAdView

        if (count % 2 == 0) {
            adUnitId = "0f5c2f42d48ab753"
            maxNativeAdView = MaxAdViewCreator.createNativeAdView(this)
        } else {
            adUnitId = "88e2f019f6ac9aef"
            maxNativeAdView = MaxAdViewCreator.createNativeAdView2(this)
        }

        val nativeAdLoader = MaxNativeAdLoader(adUnitId, this)

        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoaded(maxNativeAdView: MaxNativeAdView?, maxAd: MaxAd?) {

                nativeAdMap[nativeAdLoader] = maxAd

                viewBinding.adContainer.addView(maxNativeAdView)

                val times = count - 1

                if (times > 0) {

                    getAd(times)

                }

            }

            override fun onNativeAdLoadFailed(p0: String?, p1: MaxError?) {

                Log.e("nativeAdLoader", p1.toString())

            }

            override fun onNativeAdClicked(p0: MaxAd?) {

                Log.e("nativeAdLoader", "onNativeAdClicked")

            }
        })

        nativeAdLoader.setLocalExtraParameter(TrekMaxDataKey.CATEGORY, "news")
        nativeAdLoader.setLocalExtraParameter(
            TrekMaxDataKey.CONTENT_URL,
            "https://agirls.aotter.net/"
        )
        nativeAdLoader.setLocalExtraParameter(TrekMaxDataKey.CONTENT_TITLE, "電獺少女")

        nativeAdLoader.loadAd(maxNativeAdView)

    }



    private fun destroyAd() {

        nativeAdMap.forEach {

            val nativeAdLoader = it.key

            val nativeAd = it.value

            nativeAdLoader.destroy(nativeAd)

        }

    }

    override fun onDestroy() {
        super.onDestroy()

        destroyAd()

    }


}