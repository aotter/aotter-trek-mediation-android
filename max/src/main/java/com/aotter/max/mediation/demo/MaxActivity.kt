package com.aotter.max.mediation.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aotter.max.mediation.demo.banner_ad.MaxBannerAdActivity
import com.aotter.max.mediation.demo.databinding.ActivityMaxBinding
import com.aotter.max.mediation.demo.native_ad.MaxNativeAdRecyclerViewPageActivity
import com.aotter.max.mediation.demo.native_ad.MaxNativeAdScrollViewActivity
import com.applovin.sdk.AppLovinSdk

class MaxActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMaxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMaxBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance(this).mediationProvider = "max"

        AppLovinSdk.getInstance(this).initializeSdk {}

        initView()

    }


    private fun initView() {

        viewBinding.nativeAdScrollPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MaxNativeAdScrollViewActivity::class.java)
            startActivity(intent)
        }

        viewBinding.nativeAdRecyclerViewBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MaxNativeAdRecyclerViewPageActivity::class.java)
            startActivity(intent)
        }

        viewBinding.bannerAdBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MaxBannerAdActivity::class.java)
            startActivity(intent)
        }

    }

}