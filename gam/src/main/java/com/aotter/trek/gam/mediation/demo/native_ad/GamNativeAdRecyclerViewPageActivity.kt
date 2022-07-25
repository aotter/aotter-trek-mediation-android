package com.aotter.trek.gam.mediation.demo.native_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.aotter.trek.gam.mediation.ads.TrekGamCustomEventNative
import com.aotter.trek.gam.mediation.demo.GamLocalNativeAdData
import com.aotter.trek.gam.mediation.demo.GamNativeAdAdapter
import com.aotter.trek.gam.mediation.demo.ItemCallback
import com.aotter.trek.gam.mediation.demo.databinding.ActivityGamNativeAdRecyclerviewViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.admanager.AdManagerAdRequest

class GamNativeAdRecyclerViewPageActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGamNativeAdRecyclerviewViewBinding

    private val gamNativeAdAdapter = GamNativeAdAdapter(ItemCallback())

    private var list = mutableListOf<GamLocalNativeAdData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityGamNativeAdRecyclerviewViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        initView()

    }


    private fun initView() {

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        viewBinding.nativeAdRecyclerView.layoutManager = linearLayoutManager

        viewBinding.nativeAdRecyclerView.adapter = gamNativeAdAdapter

        list = mutableListOf<GamLocalNativeAdData>()

        repeat(12) {

            val data = GamLocalNativeAdData()

            data.postId = data.hashCode()

            list.add(
                data
            )

        }

        gamNativeAdAdapter.submitList(list.toList()){
            loadAdmobNativeAd()
        }

    }

    private fun loadAdmobNativeAd(
    ) {

        val adUnit = "ca-app-pub-8836593984677243/4613662079"

        val adLoader = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    val data = GamLocalNativeAdData()

                    data.postId = nativeAd.hashCode()

                    data.nativeAd = nativeAd

                    list.add(1,data)

                    gamNativeAdAdapter.submitList(list.toList()){

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

        bundle.putString(TrekGamDataKey.CATEGORY, "news")
        bundle.putString(TrekGamDataKey.CONTENT_URL, "https://agirls.aotter.net/")
        bundle.putString(TrekGamDataKey.CONTENT_TITLE, "電獺少女")

        val adRequest = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekGamCustomEventNative::class.java, bundle)
            .build()

        adLoader.loadAd(adRequest)

    }

    private fun loadAdmobNativeAd2(
    ) {

        val adUnit = "ca-app-pub-8836593984677243/1855351388"

        val adLoader = AdLoader.Builder(this, adUnit)
            .forNativeAd { nativeAd ->

                if (!isDestroyed) {

                    val data = GamLocalNativeAdData()

                    data.postId = nativeAd.hashCode()

                    data.nativeAd = nativeAd

                    list.add(5,data)

                    gamNativeAdAdapter.submitList(list.toList())

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

        val adRequest = AdManagerAdRequest
            .Builder()
            .addNetworkExtrasBundle(TrekGamCustomEventNative::class.java, bundle)
            .build()

        adLoader.loadAd(adRequest)

    }


}