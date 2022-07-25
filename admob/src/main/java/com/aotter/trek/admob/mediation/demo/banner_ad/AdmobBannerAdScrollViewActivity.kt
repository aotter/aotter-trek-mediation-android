package com.aotter.trek.admob.mediation.demo.banner_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.ads.TrekAdmobCustomEventBanner
import com.aotter.trek.admob.mediation.demo.databinding.ActivityAdmobBannerAdScrollViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError

class AdmobBannerAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobBannerAdScrollViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobBannerAdScrollViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        loadBannerAd()

    }

    private fun loadBannerAd() {

        viewBinding.bannerAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

                Log.i("Banner Ad", "onAdLoaded")

            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.i("Banner Ad", p0.message)
            }
        }

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = AdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekAdmobCustomEventBanner::class.java, bundle)
            .build()

        viewBinding.bannerAdView.loadAd(adRequest)

    }

}