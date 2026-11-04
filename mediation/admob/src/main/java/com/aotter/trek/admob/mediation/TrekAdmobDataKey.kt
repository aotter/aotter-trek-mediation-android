package com.aotter.trek.admob.mediation

object TrekAdmobDataKey {

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

}