package com.aotter.gam.mediation.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aotter.gam.mediation.demo.banner_ad.GamBannerAdScrollViewActivity
import com.aotter.gam.mediation.demo.databinding.ActivityGamBinding
import com.aotter.gam.mediation.demo.native_ad.GamNativeAdRecyclerViewPageActivity
import com.aotter.gam.mediation.demo.native_ad.GamNativeAdScrollViewActivity
import com.google.android.gms.ads.MobileAds

class GamActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityGamBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        MobileAds.initialize(this) {}

        initView()
    }

    private fun initView(){

        viewBinding.gamNativeAdScrollPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, GamNativeAdScrollViewActivity::class.java)
            startActivity(intent)
        }

        viewBinding.gamNativeAdRecyclerViewBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, GamNativeAdRecyclerViewPageActivity::class.java)
            startActivity(intent)
        }

        viewBinding.gamBannerAdScrollPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, GamBannerAdScrollViewActivity::class.java)
            startActivity(intent)
        }

    }

}