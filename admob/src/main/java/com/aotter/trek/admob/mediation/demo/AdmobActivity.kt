package com.aotter.trek.admob.mediation.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aotter.trek.admob.mediation.demo.banner_ad.AdmobBannerAdScrollViewActivity
import com.aotter.trek.admob.mediation.demo.databinding.ActivityAdmobBinding
import com.aotter.trek.admob.mediation.demo.native_ad.AdmobNativeAdRecyclerViewPageActivity
import com.aotter.trek.admob.mediation.demo.native_ad.AdmobNativeAdScrollViewActivity
import com.google.android.gms.ads.MobileAds

class AdmobActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        MobileAds.initialize(this) {}

        initView()
    }

    private fun initView(){

        viewBinding.admobNativeAdScrollPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, AdmobNativeAdScrollViewActivity::class.java)
            startActivity(intent)
        }

        viewBinding.admobNativeAdRecyclerViewBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, AdmobNativeAdRecyclerViewPageActivity::class.java)
            startActivity(intent)
        }

        viewBinding.admobBannerAdScrollPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, AdmobBannerAdScrollViewActivity::class.java)
            startActivity(intent)
        }

    }

}