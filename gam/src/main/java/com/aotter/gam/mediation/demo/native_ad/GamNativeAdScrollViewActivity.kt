package com.aotter.gam.mediation.demo.native_ad

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.aotter.gam.mediation.demo.databinding.ActivityGamNativeAdScrollViewBinding
import com.aotter.gam.mediation.demo.databinding.ItemStyle1Binding
import com.aotter.gam.mediation.demo.databinding.ItemStyle2Binding
import com.aotter.gam.mediation.demo.databinding.ItemStyle3Binding
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.aotter.trek.gam.mediation.ads.TrekGamCustomEventNative
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import kotlin.math.roundToInt


class GamNativeAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGamNativeAdScrollViewBinding

    private lateinit var adLoader: AdLoader

    private lateinit var adRequest: AdRequest

    private lateinit var adLoader2: AdLoader

    private lateinit var adRequest2: AdRequest

    private lateinit var adLoader3: AdLoader

    private lateinit var adRequest3: AdRequest

    private var adView: ItemStyle1Binding? = null

    private var adView2: ItemStyle2Binding? = null

    private var adView3: ItemStyle3Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityGamNativeAdScrollViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        loadAdmobNativeAd()

        loadAdmobNativeAd2()

        loadAdmobNativeAd3()

    }

    private fun loadAdmobNativeAd() {

        val adUnit = "ca-app-pub-8836593984677243/4613662079"

        adLoader = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    adView = ItemStyle1Binding.bind(viewBinding.viewStub.inflate()).apply {

                        advertiser.text = nativeAd.advertiser

                        adBody.text = nativeAd.headline

                        mediaView.mediaContent = nativeAd.mediaContent

                        mediaView.setImageScaleType(ImageView.ScaleType.FIT_XY)

                        if ((nativeAd.mediaContent?.aspectRatio ?: 0.0f) > 1f) {
                            mediaView.post {

                                val height = (this.root.measuredWidth * 0.5625f).roundToInt()

                                mediaView.layoutParams.height = height

                                mediaView.requestLayout()

                            }
                        }

                        nativeAdView.mediaView = mediaView

                        nativeAdView.headlineView = adBody

                        nativeAdView.advertiserView = advertiser

                        nativeAdView.setNativeAd(nativeAd)

                    }


                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.i("adLoader", "onAdClicked")
                }

                override fun onAdImpression() {
                    super.onAdImpression()

                    Log.i("adLoader", "onAdImpression")

                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.i("adLoader", "onAdLoaded")
                }
            })
            .build()

        val bundle = Bundle()

        bundle.putString(TrekGamDataKey.CATEGORY, "news")
        bundle.putString(TrekGamDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekGamDataKey.CONTENT_TITLE, "電獺少女")

        adRequest = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekGamCustomEventNative::class.java, bundle)
            .build()

        adLoader.loadAd(adRequest)

    }

    private fun loadAdmobNativeAd2() {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        adLoader2 = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    adView2 = ItemStyle2Binding.bind(viewBinding.viewStub2.inflate()).apply {

                        advertiser.text = nativeAd.advertiser

                        adBody.text = nativeAd.headline

                        val mainImg = nativeAd.extras.getString(TrekAdmobDataKey.MAIN_IMAGE)
                            ?: nativeAd.mediaContent?.mainImage

                        Glide.with(this@GamNativeAdScrollViewActivity)
                            .load(mainImg)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(adImg)

                        nativeAdView.headlineView = adBody

                        nativeAdView.advertiserView = advertiser

                        nativeAdView.imageView = adImg

                        nativeAdView.setNativeAd(nativeAd)

                    }

                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.i("adLoader", "onAdClicked2")
                }

                override fun onAdImpression() {
                    super.onAdImpression()

                    Log.i("adLoader", "onAdImpression2")

                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.i("adLoader", "onAdLoaded2")
                }
            })
            .build()

        val bundle = Bundle()

        bundle.putString(TrekGamDataKey.CATEGORY, "news")
        bundle.putString(TrekGamDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekGamDataKey.CONTENT_TITLE, "電獺少女")

        adRequest2 = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekGamCustomEventNative::class.java, bundle)
            .build()

        adLoader2.loadAd(adRequest2)

    }

    private fun loadAdmobNativeAd3() {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        adLoader3 = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    adView3 = ItemStyle3Binding.bind(viewBinding.viewStub3.inflate()).apply {

                        advertiser.text = nativeAd.advertiser

                        adBody.text = nativeAd.headline

                        Glide.with(this@GamNativeAdScrollViewActivity)
                            .load(nativeAd.icon?.drawable?:"")
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(adImg)

                        nativeAdView.headlineView = adBody

                        nativeAdView.advertiserView = advertiser

                        nativeAdView.imageView = adImg

                        nativeAdView.setNativeAd(nativeAd)

                    }

                }
            }
            .withAdListener(
                object : AdListener() {
                    override fun onAdClicked() {
                        super.onAdClicked()
                        Log.i("adLoader", "onAdClicked2")
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()

                        Log.i("adLoader", "onAdImpression2")

                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.i("adLoader", "onAdLoaded2")
                    }
                })
            .build()

        val bundle = Bundle()

        bundle.putString(TrekGamDataKey.CATEGORY, "news")
        bundle.putString(TrekGamDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekGamDataKey.CONTENT_TITLE, "電獺少女")

        adRequest3 = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(
                TrekGamCustomEventNative::
                class.java, bundle
            )
            .build()

        adLoader3.loadAd(adRequest3)

    }

    override fun onDestroy() {
        super.onDestroy()

        adView?.nativeAdView?.destroy()

        adView2?.nativeAdView?.destroy()

        adView3?.nativeAdView?.destroy()

    }

}