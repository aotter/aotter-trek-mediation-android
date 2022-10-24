package com.aotter.trek.gam.mediation.ads


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.aotter.net.dto.trek.response.Img
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekAdViewBinder
import com.aotter.net.trek.ads.TrekMediaView
import com.aotter.trek.gam.mediation.TrekGamDataKey
import com.google.android.gms.ads.formats.NativeAd
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper
import com.google.android.gms.ads.nativead.NativeAdView

class TrekGamUnifiedNativeAdMapper(private val context: Context) : UnifiedNativeAdMapper() {

    companion object {

        private const val IMAGE_SCALE = 1.0

    }

    private var trekNativeAd: TrekNativeAd? = null

    private var trekAdViewBinder: TrekAdViewBinder? = null

    private val trekMediaView by lazy {

        TrekMediaView(context, null)

    }

    init {

        overrideClickHandling = true

        overrideImpressionRecording = true

    }

    fun mappingNativeData(trekNativeAd: TrekNativeAd) {

        this.trekNativeAd = trekNativeAd

        headline = trekNativeAd.title ?: ""

        body = trekNativeAd.text ?: ""

        callToAction = trekNativeAd.callToAction ?: ""

        advertiser = trekNativeAd.advertiserName ?: ""

        val emptyIconBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)

        icon = TrekGamNativeMappedImage(
            trekNativeAd.imgIconHd.drawable ?: BitmapDrawable(
                context.resources,
                emptyIconBitmap
            ),
            trekNativeAd.imgIconHd.uri ?: Uri.parse(""),
            IMAGE_SCALE
        )

        setTrekImagesToAdmobImages(trekNativeAd.imgs)

        price = ""

        starRating = 0.0

        store = ""

        setHasVideoContent(trekNativeAd.isVideoAd())

        setMediaView(trekMediaView)

        val bundle = Bundle()

        bundle.putString(TrekGamDataKey.SPONSOR, trekNativeAd.sponsor)

        bundle.putString(TrekGamDataKey.MAIN_IMAGE, trekNativeAd.imgMain.uri.toString())

        bundle.putString(TrekGamDataKey.ICON_IMAGE, trekNativeAd.imgIcon.uri.toString())

        bundle.putString(TrekGamDataKey.ICON_IMAGE_HD, trekNativeAd.imgIconHd.uri.toString())

        extras = bundle

    }

    private fun setTrekImagesToAdmobImages(trekImages: List<Img>) {

        val imageList = mutableListOf<NativeAd.Image>()

        trekImages.forEach { img ->

            val width = if (img.width == 0) {
                1
            } else {
                img.width
            }

            val height = if (img.height == 0) {
                1
            } else {
                img.height
            }

            val emptyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            imageList.add(
                TrekGamNativeMappedImage(
                    img.image.drawable ?: BitmapDrawable(
                        context.resources,
                        emptyBitmap
                    ), img.image.uri ?: Uri.parse(""), IMAGE_SCALE
                )
            )
        }

        images = imageList

    }

    override fun trackViews(
        containerView: View,
        clickableAssetViews: MutableMap<String, View>,
        nonClickableAssetViews: MutableMap<String, View>
    ) {
        super.trackViews(containerView, clickableAssetViews, nonClickableAssetViews)

        trekNativeAd?.let { trekNativeAd ->

            (containerView as? NativeAdView)?.let { nativeAdView ->

                val mediaView: TrekMediaView? =
                    nativeAdView.mediaView?.findViewWithTag(trekMediaView.tag)

                trekAdViewBinder = TrekAdViewBinder(containerView, mediaView, trekNativeAd).apply {

                    clickableAssetViews.values.forEach { view ->

                        this.setViewClick(view)

                    }

                    this.bindAdView()

                }


            }

        }

    }

    override fun untrackView(view: View) {
        super.untrackView(view)

        trekAdViewBinder?.destroy()

    }

}