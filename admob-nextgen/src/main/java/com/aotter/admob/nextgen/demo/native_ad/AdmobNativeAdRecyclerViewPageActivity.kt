package com.aotter.admob.nextgen.demo.native_ad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aotter.admob.nextgen.demo.AdmobLocalNativeAdData
import com.aotter.admob.nextgen.demo.AdmobNativeAdAdapter
import com.aotter.admob.nextgen.demo.ItemCallback
import com.aotter.admob.nextgen.demo.R
import com.aotter.admob.nextgen.demo.databinding.ActivityAdmobNativeAdRecyclerviewViewBinding
import com.aotter.admob.nextgen.demo.databinding.ItemAdmobNativeAdBinding
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

class AdmobNativeAdRecyclerViewPageActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobNativeAdRecyclerviewViewBinding

    private val admobNativeAdAdapter = AdmobNativeAdAdapter(ItemCallback())

    private var list = mutableListOf<AdmobLocalNativeAdData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobNativeAdRecyclerviewViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Edge-to-edge (enforced on Android 15+): appcompat 1.7 hands the combined
        // status-bar + action-bar inset to the content view; pad so nothing is covered.
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            WindowInsetsCompat.CONSUMED
        }


        initView()

    }


    private fun initView() {

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        viewBinding.nativeAdRecyclerView.layoutManager = linearLayoutManager

        viewBinding.nativeAdRecyclerView.adapter = admobNativeAdAdapter

        list = mutableListOf<AdmobLocalNativeAdData>()

        repeat(12) {

            val data = AdmobLocalNativeAdData()

            data.postId = data.hashCode()

            list.add(
                data
            )

        }

        admobNativeAdAdapter.submitList(list.toList()) {
            loadAdmobNativeAd()
        }

    }

    private fun loadAdmobNativeAd(
    ) {

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

                    ad.adEventCallback = object : NativeAdEventCallback {
                        override fun onAdClicked() {
                            Log.i("adLoader", "onAdClicked")
                        }

                        override fun onAdImpression() {
                            Log.i("adLoader", "onAdImpression")
                        }
                    }

                    val data = AdmobLocalNativeAdData()

                    data.postId = ad.hashCode()

                    data.adView = createAdView(ad, true)

                    list.add(4, data)

                    admobNativeAdAdapter.submitList(list.toList()) {

                        loadAdmobNativeAd2()

                    }

                }

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.w("adLoader", "onAdFailedToLoad: $adError")
            }

        })

    }

    private fun loadAdmobNativeAd2(
    ) {

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

                    ad.adEventCallback = object : NativeAdEventCallback {
                        override fun onAdClicked() {
                            Log.i("adLoader", "onAdClicked2")
                        }

                        override fun onAdImpression() {
                            Log.i("adLoader", "onAdImpression2")
                        }
                    }

                    val data = AdmobLocalNativeAdData()

                    data.postId = ad.hashCode()

                    data.adView = createAdView(ad, false)

                    list.add(8, data)

                    admobNativeAdAdapter.submitList(list.toList())

                }

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.w("adLoader", "onAdFailedToLoad2: $adError")
            }

        })

    }

    private fun createAdView(nativeAd: NativeAd, isMedia: Boolean): View {

        val adView = ItemAdmobNativeAdBinding.bind(
            LayoutInflater.from(this)
                .inflate(R.layout.item_admob_native_ad, null)
        )

        adView.advertiser.text = nativeAd.advertiser

        adView.adBody.text = nativeAd.body

        Glide.with(this)
            .load(nativeAd.icon?.drawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(adView.adImg)

        val mediaView = adView.mediaView

        if (isMedia) {

            mediaView.imageScaleType = ImageView.ScaleType.FIT_XY

            // Always pin the media height. Google demand rendered as HTML/video
            // reports no aspect ratio, and a wrap_content MediaView inside this
            // unbounded list would grow without limit.
            val ratio = nativeAd.mediaContent?.aspectRatio?.takeIf { it > 0f } ?: (16f / 9f)

            mediaView.post {

                val height = (mediaView.measuredWidth / ratio).roundToInt()

                mediaView.layoutParams.height = height

                mediaView.requestLayout()

            }

        } else {

            // The legacy demo skipped the MediaView entirely for this style; the
            // Next-Gen registerNativeAd call requires one, so collapse it instead.
            mediaView.visibility = View.GONE

        }

        adView.nativeAdView.registerNativeAd(nativeAd, mediaView)

        return adView.root

    }


}
