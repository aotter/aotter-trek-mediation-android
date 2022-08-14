package com.aotter.max.mediation.demo.native_ad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aotter.trek.max.mediation.TrekMaxDataKey
import com.aotter.max.mediation.demo.ItemCallback
import com.aotter.max.mediation.demo.MaxAdViewCreator
import com.aotter.max.mediation.demo.MaxLocalNativeAdData
import com.aotter.max.mediation.demo.MaxNativeAdAdapter
import com.aotter.max.mediation.demo.databinding.ActivityMaxNativeAdRecyclerViewBinding
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView

class MaxNativeAdRecyclerViewPageActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMaxNativeAdRecyclerViewBinding

    private val maxNativeAdAdapter = MaxNativeAdAdapter(ItemCallback())

    private var list = mutableListOf<MaxLocalNativeAdData>()

    private var nativeAdMap = hashMapOf<MaxNativeAdLoader, MaxAd?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMaxNativeAdRecyclerViewBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        initView()

        viewBinding.refreshBtn.setOnClickListener {

            destroy()

            initView()

        }

    }

    private fun initView() {

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        viewBinding.nativeAdRecyclerView.layoutManager = linearLayoutManager

        viewBinding.nativeAdRecyclerView.adapter = maxNativeAdAdapter

        list = mutableListOf<MaxLocalNativeAdData>()

        repeat(15) {

            val data =  MaxLocalNativeAdData()

            data.postId = data.hashCode()

            list.add(
                data
            )

        }

        maxNativeAdAdapter.submitList(list) {
            getAd(list.count())
        }

    }

    private fun getAd(count: Int) {

        val adUnitId: String

        val maxNativeAdView: MaxNativeAdView

        if (count % 2 == 0) {
            adUnitId = "06acea3d1f3245c7"
            maxNativeAdView = MaxAdViewCreator.createNativeAdView(this)
        } else {
            adUnitId = "a1e6220b3f0907ee"
            maxNativeAdView = MaxAdViewCreator.createNativeAdView2(this)
        }

        val nativeAdLoader = MaxNativeAdLoader(adUnitId, this)

        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoaded(maxNativeAdView: MaxNativeAdView?, maxAd: MaxAd?) {

                nativeAdMap[nativeAdLoader] = maxAd

                val position = count - 1

                if (position > 0) {

                    val data = MaxLocalNativeAdData()

                    data.postId = maxAd.hashCode()

                    data.maxNativeAdView = maxNativeAdView

                    list.add(position, data)

                    maxNativeAdAdapter.submitList(list.toList()) {
                        getAd(position)
                    }

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

    private fun destroy() {

        nativeAdMap.forEach {

            val nativeAdLoader = it.key

            val nativeAd = it.value

            nativeAdLoader.destroy(nativeAd)

        }

        list.clear()

    }

    override fun onDestroy() {
        super.onDestroy()

        destroy()

    }

}