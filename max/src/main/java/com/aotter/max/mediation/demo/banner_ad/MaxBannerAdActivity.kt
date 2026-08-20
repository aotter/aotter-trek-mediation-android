package com.aotter.max.mediation.demo.banner_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotter.max.mediation.demo.databinding.ActivityMaxBannerAdBinding
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MaxBannerAdActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMaxBannerAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMaxBannerAdBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Edge-to-edge (enforced on Android 15+): appcompat 1.7 hands the combined
        // status-bar + action-bar inset to the content view; pad so nothing is covered.
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            WindowInsetsCompat.CONSUMED
        }


        getBannerAd()

    }

    private fun getBannerAd() {

        viewBinding.maxBannerAd.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

                Log.e("maxBannerAd", "onAdLoaded")

            }

            override fun onAdDisplayed(ad: MaxAd?) {
                Log.e("maxBannerAd", "onAdDisplayed")
            }

            override fun onAdHidden(ad: MaxAd?) {
                Log.e("maxBannerAd", "onAdHidden")
            }

            override fun onAdClicked(ad: MaxAd?) {
                Log.e("maxBannerAd", "onAdClicked")
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                Log.e("maxBannerAd", "onAdLoadFailed")
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                Log.e("maxBannerAd", "onAdDisplayFailed")
            }

            override fun onAdExpanded(ad: MaxAd?) {
                Log.e("maxBannerAd", "onAdExpanded")
            }

            override fun onAdCollapsed(ad: MaxAd?) {
                Log.e("maxBannerAd", "onAdCollapsed")
            }

        })

        viewBinding.maxBannerAd.setLocalExtraParameter(TrekMaxDataKey.CATEGORY, "news")
        viewBinding.maxBannerAd.setLocalExtraParameter(
            TrekMaxDataKey.CONTENT_URL,
            "https://agirls.aotter.net/"
        )
        viewBinding.maxBannerAd.setLocalExtraParameter(TrekMaxDataKey.CONTENT_TITLE, "電獺少女")


        viewBinding.maxBannerAd.loadAd()

    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding.maxBannerAd.destroy()

    }

}