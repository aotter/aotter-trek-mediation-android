package com.aotter.admob.mediation.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aotter.admob.mediation.demo.banner_ad.AdmobBannerAdScrollViewActivity
import com.aotter.admob.mediation.demo.databinding.ActivityAdmobBinding
import com.aotter.admob.mediation.demo.native_ad.AdmobNativeAdRecyclerViewPageActivity
import com.aotter.admob.mediation.demo.native_ad.AdmobNativeAdScrollViewActivity
import com.google.android.gms.ads.MobileAds
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdmobActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAdmobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityAdmobBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        // Edge-to-edge (enforced on Android 15+): appcompat 1.7 hands the combined
        // status-bar + action-bar inset to the content view; pad so nothing is covered.
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            WindowInsetsCompat.CONSUMED
        }


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