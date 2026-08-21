package com.aotter.admob.nextgen.demo.banner_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotter.admob.nextgen.demo.databinding.ActivityAdmobBannerAdScrollViewBinding
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.ads.TrekAdmobCustomEventBanner
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdmobBannerAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobBannerAdScrollViewBinding

    private var bannerAd: BannerAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobBannerAdScrollViewBinding.inflate(layoutInflater)

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

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        // The Next-Gen SDK has no XML AdView with adUnitId/adSize attributes: the
        // banner is loaded here and its view added into the layout's container.
        // putAdSourceExtrasBundle is the Next-Gen equivalent of addNetworkExtrasBundle.
        val adRequest = BannerAdRequest.Builder(BANNER_AD_UNIT, AdSize.BANNER)
            .putAdSourceExtrasBundle(TrekAdmobCustomEventBanner::class.java, bundle)
            .build()

        BannerAd.load(adRequest, object : AdLoadCallback<BannerAd> {

            override fun onAdLoaded(ad: BannerAd) {

                Log.i("Banner Ad", "onAdLoaded")

                // Next-Gen SDK callbacks arrive on a background thread; hop to
                // the main thread before touching views.
                runOnUiThread {

                    if (isDestroyed) {
                        ad.destroy()
                        return@runOnUiThread
                    }

                    bannerAd = ad

                    ad.adEventCallback = object : BannerAdEventCallback {

                        override fun onAdImpression() {
                            Log.i("Banner Ad", "onAdImpression")
                        }

                        override fun onAdClicked() {
                            Log.i("Banner Ad", "onAdClicked")
                        }

                    }

                    viewBinding.bannerAdView.addView(ad.getView(this@AdmobBannerAdScrollViewActivity))

                }

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.i("Banner Ad", adError.message)
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()

        bannerAd?.destroy()

    }

    companion object {

        private const val BANNER_AD_UNIT = "ca-app-pub-8836593984677243/2093351036"

    }

}
