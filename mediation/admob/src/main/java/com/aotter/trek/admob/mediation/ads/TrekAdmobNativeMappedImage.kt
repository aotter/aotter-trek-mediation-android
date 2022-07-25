package com.aotter.trek.admob.mediation.ads

import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.android.gms.ads.formats.NativeAd

class TrekAdmobNativeMappedImage(
    private val drawable: Drawable,
    private val uri: Uri,
    private val scale: Double
) : NativeAd.Image() {

    override fun getScale(): Double {
        return scale
    }

    override fun getDrawable(): Drawable {
        return drawable
    }

    override fun getUri(): Uri {
        return uri
    }
}