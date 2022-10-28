package com.aotter.admob.mediation.demo.native_ad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aotter.admob.mediation.demo.AdmobLocalNativeAdData
import com.aotter.admob.mediation.demo.AdmobNativeAdAdapter
import com.aotter.admob.mediation.demo.ItemCallback
import com.aotter.admob.mediation.demo.R
import com.aotter.admob.mediation.demo.databinding.ActivityAdmobNativeAdRecyclerviewViewBinding
import com.aotter.admob.mediation.demo.databinding.ItemAdmobNativeAdBinding
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.aotter.trek.admob.mediation.ads.TrekAdmobCustomEventNative
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import kotlin.math.roundToInt

class AdmobNativeAdRecyclerViewPageActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobNativeAdRecyclerviewViewBinding

    private val admobNativeAdAdapter = AdmobNativeAdAdapter(ItemCallback())

    private var list = mutableListOf<AdmobLocalNativeAdData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobNativeAdRecyclerviewViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

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

        val adLoader = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    val data = AdmobLocalNativeAdData()

                    data.postId = nativeAd.hashCode()

                    data.adView = createAdView(nativeAd,true)

                    list.add(1, data)

                    admobNativeAdAdapter.submitList(list.toList()) {

                        loadAdmobNativeAd2()

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

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = AdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekAdmobCustomEventNative::class.java, bundle)
            .build()

        adLoader.loadAd(adRequest)

    }

    private fun loadAdmobNativeAd2(
    ) {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        val adLoader = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    val data = AdmobLocalNativeAdData()

                    data.postId = nativeAd.hashCode()

                    data.adView = createAdView(nativeAd,false)

                    list.add(8, data)

                    admobNativeAdAdapter.submitList(list.toList())

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

        bundle.putString(TrekAdmobDataKey.CATEGORY, "news")
        bundle.putString(TrekAdmobDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekAdmobDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = AdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekAdmobCustomEventNative::class.java, bundle)
            .build()

        adLoader.loadAd(adRequest)

    }

    private fun createAdView(nativeAd: NativeAd,isMedia:Boolean): View {

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

        val mediaView = if (isMedia) {
            adView.mediaView
        } else {
            null
        }

        mediaView?.mediaContent = nativeAd.mediaContent

        mediaView?.setImageScaleType(ImageView.ScaleType.FIT_XY)

        if ((nativeAd.mediaContent?.aspectRatio ?: 0.0f) > 1f) {
            mediaView?.post {

                val height = (adView.root.measuredWidth * 0.5625f).roundToInt()

                mediaView.layoutParams.height = height

                mediaView.requestLayout()

            }
        }

        adView.nativeAdView.mediaView = mediaView

        adView.nativeAdView.setNativeAd(nativeAd)

        return adView.root

    }


}