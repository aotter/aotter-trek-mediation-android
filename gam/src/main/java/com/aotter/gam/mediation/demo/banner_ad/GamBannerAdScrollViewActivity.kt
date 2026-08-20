package com.aotter.gam.mediation.demo.banner_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotter.gam.mediation.demo.databinding.ActivityGamBannerAdScrollViewBinding
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.aotter.trek.gam.mediation.ads.TrekGamCustomEventBanner
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GamBannerAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGamBannerAdScrollViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityGamBannerAdScrollViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Edge-to-edge (enforced on Android 15+): appcompat 1.7 hands the combined
        // status-bar + action-bar inset to the content view; pad so nothing is covered.
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            WindowInsetsCompat.CONSUMED
        }


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

        bundle.putString(TrekGamDataKey.CATEGORY, "news")
        bundle.putString(TrekGamDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekGamDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekGamCustomEventBanner::class.java, bundle)
            .build()

        viewBinding.bannerAdView.loadAd(adRequest)

    }

}