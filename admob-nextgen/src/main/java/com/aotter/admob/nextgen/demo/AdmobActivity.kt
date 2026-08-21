package com.aotter.admob.nextgen.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aotter.admob.nextgen.demo.banner_ad.AdmobBannerAdScrollViewActivity
import com.aotter.admob.nextgen.demo.databinding.ActivityAdmobBinding
import com.aotter.admob.nextgen.demo.native_ad.AdmobNativeAdRecyclerViewPageActivity
import com.aotter.admob.nextgen.demo.native_ad.AdmobNativeAdScrollViewActivity
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig
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


        // Unlike the legacy SDK, the Next-Gen SDK takes the AdMob app ID here in
        // code; mediation adapters (Trek included) are initialized during this call.
        MobileAds.initialize(this, InitializationConfig.Builder(ADMOB_APP_ID).build())

        Log.i(TAG, "GMA Next-Gen SDK version: ${MobileAds.getVersion()}")

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

    companion object {

        private const val TAG = "NextGenAdmob"

        private const val ADMOB_APP_ID = "ca-app-pub-8836593984677243~2250388465"

    }

}
