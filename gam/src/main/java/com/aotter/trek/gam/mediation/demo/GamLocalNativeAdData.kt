package com.aotter.trek.gam.mediation.demo

import com.google.android.gms.ads.nativead.NativeAd

data class GamLocalNativeAdData(
    var postId: Int = 0,
    var title: String = "幸運調色盤：12星座明天穿什麼？(6/6-6/12)",
    var advertiser: String = "電獺少女",
    var img: String = "http://pnn.aotter.net/Media/show/d8404d54-aab7-4729-8e85-64fb6b92a84e.jpg",
    var nativeAd: NativeAd? = null
)