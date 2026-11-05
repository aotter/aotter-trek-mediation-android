package com.aotter.trek.max.mediation

import com.aotter.net.trek.ads.TrekAdError
import com.applovin.mediation.adapter.MaxAdapterError

object TrekMaxDataKey {

    const val CATEGORY = "Category"
    const val CONTENT_URL = "ContentUrl"
    const val CONTENT_TITLE = "ContentTitle"
    const val MAIN_IMAGE = "MainImage"
    const val ICON_IMAGE = "IconImage"
    const val ICON_IMAGE_HD = "IconImageHd"
    const val SPONSOR = "Sponsor"

    const val AD_SIZE_WIDTH = "adSizeWidth"
    const val AD_SIZE_HEIGHT = "adSizeHeight"

    const val AD_TYPE = "adType"

    //The Trek placement type the ad was served for, in the same three values as the iOS adapter:
    //the server's SUPR_AD is NATIVE_MULTIMEDIA, and any other value gets no AD_TYPE entry
    internal fun adTypeOf(serverAdType: String): String? = when (serverAdType) {
        "NATIVE" -> "NATIVE"
        "SUPR_AD" -> "NATIVE_MULTIMEDIA"
        "BANNER" -> "BANNER"
        else -> null
    }

    //Trek no fill (800) is reported as no fill, every other Trek failure as an internal error
    internal fun maxAdapterErrorOf(trekErrorCode: Int): MaxAdapterError =
        if (trekErrorCode == TrekAdError.NO_AD) MaxAdapterError.NO_FILL else MaxAdapterError.INTERNAL_ERROR

}