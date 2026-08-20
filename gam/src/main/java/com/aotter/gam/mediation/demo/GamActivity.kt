package com.aotter.gam.mediation.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aotter.gam.mediation.demo.banner_ad.GamBannerAdScrollViewActivity
import com.aotter.gam.mediation.demo.databinding.ActivityGamBinding
import com.aotter.gam.mediation.demo.native_ad.GamNativeAdRecyclerViewPageActivity
import com.aotter.gam.mediation.demo.native_ad.GamNativeAdScrollViewActivity
import com.google.android.gms.ads.MobileAds
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GamActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityGamBinding.inflate(layoutInflater)

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