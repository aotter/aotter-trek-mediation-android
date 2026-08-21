package com.aotter.admob.nextgen.demo.native_ad

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.aotter.admob.nextgen.demo.databinding.ActivityAdmobNativeAdScrollViewBinding
import com.aotter.admob.nextgen.demo.databinding.ItemStyle1Binding
import com.aotter.admob.nextgen.demo.databinding.ItemStyle2Binding
import com.aotter.admob.nextgen.demo.databinding.ItemStyle3Binding
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.ads.TrekAdmobCustomEventNative
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoader
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoaderCallback
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdRequest
import kotlin.math.roundToInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class AdmobNativeAdScrollViewActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobNativeAdScrollViewBinding

    private var adView: ItemStyle1Binding? = null

    private var adView2: ItemStyle2Binding? = null

    private var adView3: ItemStyle3Binding? = null

    private var nativeAd: NativeAd? = null

    private var nativeAd2: NativeAd? = null

    private var nativeAd3: NativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobNativeAdScrollViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Edge-to-edge (enforced on Android 15+): appcompat 1.7 hands the combined
        // status-bar + action-bar inset to the content view; pad so nothing is covered.
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            WindowInsetsCompat.CONSUMED
        }


        loadAdmobNativeAd()

        loadAdmobNativeAd2()

        loadAdmobNativeAd3()

    }

    private fun loadAdmobNativeAd() {

        val adUnit = "ca-app-pub-8836593984677243/4613662079"

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        // putAdSourceExtrasBundle is the Next-Gen equivalent of the legacy
        // addNetworkExtrasBundle: same adapter class key, same bundle.
        val adRequest = NativeAdRequest
            .Builder(adUnit, listOf(NativeAd.NativeAdType.NATIVE))
            .putAdSourceExtrasBundle(TrekAdmobCustomEventNative::class.java, bundle)
            .build()

        NativeAdLoader.load(adRequest, object : NativeAdLoaderCallback {

            override fun onNativeAdLoaded(ad: NativeAd) {

                Log.i("adLoader", "onAdLoaded")

                // Next-Gen SDK callbacks arrive on a background thread; hop to
                // the main thread before touching views.
                runOnUiThread {

                    if (isDestroyed) {
                        ad.destroy()
                        return@runOnUiThread
                    }

                    nativeAd = ad

                    ad.adEventCallback = object : NativeAdEventCallback {
                        override fun onAdClicked() {
                            Log.i("adLoader", "onAdClicked")
                        }

                        override fun onAdImpression() {
                            Log.i("adLoader", "onAdImpression")
                        }
                    }

                    adView = ItemStyle1Binding.bind(viewBinding.viewStub.inflate()).apply {

                        advertiser.text = ad.advertiser

                        adBody.text = ad.body

                        mediaView.imageScaleType = ImageView.ScaleType.FIT_XY

                        // Always pin the media height. Google demand rendered as HTML/video
                        // reports no aspect ratio, and a wrap_content MediaView inside this
                        // unbounded scroll container would grow without limit.
                        val ratio = ad.mediaContent?.aspectRatio?.takeIf { it > 0f } ?: (16f / 9f)

                        mediaView.post {

                            val height = (mediaView.measuredWidth / ratio).roundToInt()

                            mediaView.layoutParams.height = height

                            mediaView.requestLayout()

                        }

                        nativeAdView.registerNativeAd(ad, mediaView)

                    }

                }

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.w("adLoader", "onAdFailedToLoad: $adError")
            }

        })

    }

    private fun loadAdmobNativeAd2() {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = NativeAdRequest
            .Builder(adUnit, listOf(NativeAd.NativeAdType.NATIVE))
            .putAdSourceExtrasBundle(TrekAdmobCustomEventNative::class.java, bundle)
            .build()

        NativeAdLoader.load(adRequest, object : NativeAdLoaderCallback {

            override fun onNativeAdLoaded(ad: NativeAd) {

                Log.i("adLoader", "onAdLoaded2")

                runOnUiThread {

                    if (isDestroyed) {
                        ad.destroy()
                        return@runOnUiThread
                    }

                    nativeAd2 = ad

                    ad.adEventCallback = object : NativeAdEventCallback {
                        override fun onAdClicked() {
                            Log.i("adLoader", "onAdClicked2")
                        }

                        override fun onAdImpression() {
                            Log.i("adLoader", "onAdImpression2")
                        }
                    }

                    adView2 = ItemStyle2Binding.bind(viewBinding.viewStub2.inflate()).apply {

                        advertiser.text = ad.advertiser

                        adBody.text = ad.body

                        val mainImg = ad.extras.getString(TrekAdmobDataKey.MAIN_IMAGE)
                            ?: ad.mediaContent?.mainImage

                        Glide.with(this@AdmobNativeAdScrollViewActivity).load(mainImg)
                            .transition(DrawableTransitionOptions.withCrossFade()).into(adImg)

                        nativeAdView.headlineView = adBody

                        nativeAdView.advertiserView = advertiser

                        // The Next-Gen NativeAdView has no imageView asset slot (the
                        // legacy demo registered adImg there); the layout's invisible
                        // MediaView satisfies registerNativeAd instead.
                        nativeAdView.registerNativeAd(ad, mediaView)

                    }

                }

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.w("adLoader", "onAdFailedToLoad2: $adError")
            }

        })

    }

    private fun loadAdmobNativeAd3() {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = NativeAdRequest
            .Builder(adUnit, listOf(NativeAd.NativeAdType.NATIVE))
            .putAdSourceExtrasBundle(TrekAdmobCustomEventNative::class.java, bundle)
            .build()

        NativeAdLoader.load(adRequest, object : NativeAdLoaderCallback {

            override fun onNativeAdLoaded(ad: NativeAd) {

                Log.i("adLoader", "onAdLoaded3")

                runOnUiThread {

                    if (isDestroyed) {
                        ad.destroy()
                        return@runOnUiThread
                    }

                    nativeAd3 = ad

                    ad.adEventCallback = object : NativeAdEventCallback {
                        override fun onAdClicked() {
                            Log.i("adLoader", "onAdClicked3")
                        }

                        override fun onAdImpression() {
                            Log.i("adLoader", "onAdImpression3")
                        }
                    }

                    adView3 = ItemStyle3Binding.bind(viewBinding.viewStub3.inflate()).apply {

                        advertiser.text = ad.advertiser

                        adBody.text = ad.body

                        Glide.with(this@AdmobNativeAdScrollViewActivity)
                            .load(ad.icon?.drawable ?: "")
                            .transition(DrawableTransitionOptions.withCrossFade()).into(adImg)

                        nativeAdView.headlineView = adBody

                        nativeAdView.advertiserView = advertiser

                        nativeAdView.registerNativeAd(ad, mediaView)

                    }

                }
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.w("adLoader", "onAdFailedToLoad3: $adError")
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()

        nativeAd?.destroy()

        nativeAd2?.destroy()

        nativeAd3?.destroy()

    }

}
