package com.aotter.trek.admob.mediation.ads


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.aotter.net.dto.trek.response.ImgSrc
import com.aotter.net.dto.trek.response.TrekNativeAd
import com.aotter.net.trek.ads.TrekMediaView
import com.aotter.net.utils.TrekAdViewUtils
import com.aotter.trek.admob.mediation.TrekAdmobDataKey
import com.google.android.gms.ads.formats.NativeAd
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper
import com.google.android.gms.ads.nativead.NativeAdAssetNames

class TrekAdmobUnifiedNativeAdMapper(private val context: Context) : UnifiedNativeAdMapper() {

    companion object {

        private const val IMAGE_SCALE = 1.0

    }

    private var trekNativeAd: TrekNativeAd? = null

    private val trekMediaView by lazy {

        TrekMediaView(context)

    }

    init {

        overrideClickHandling = true

        overrideImpressionRecording = true

    }

    fun mappingNativeData(trekNativeAd: TrekNativeAd) {

        this.trekNativeAd = trekNativeAd

        headline = trekNativeAd.headline ?: ""

        body = trekNativeAd.body ?: ""

        callToAction = trekNativeAd.callToAction ?: ""

        advertiser = trekNativeAd.advertiserName ?: ""

        val emptyIconBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)

        icon = TrekAdmobNativeMappedImage(
            trekNativeAd.imgIconHd.drawable ?: BitmapDrawable(
                context.resources, emptyIconBitmap
            ), trekNativeAd.imgIconHd.uri ?: Uri.parse(""), IMAGE_SCALE
        )

        setTrekImagesToAdmobImages(trekNativeAd.images)

        price = ""

        starRating = 0.0

        store = ""

        setHasVideoContent(trekNativeAd.isVideoAd())

        trekNativeAd.mediaContentAspectRatio?.takeIf { it > 0f && it.isFinite() }?.let {

            //Trek reports aspectRatio as height/width, AdMob MediaContent defines it as
            //width/height, so it has to be inverted
            mediaContentAspectRatio = 1f / it

        }

        setMediaView(trekMediaView)

        val bundle = Bundle()

        bundle.putString(TrekAdmobDataKey.SPONSOR, trekNativeAd.sponsor)

        bundle.putString(TrekAdmobDataKey.MAIN_IMAGE, trekNativeAd.imgMain.uri.toString())

        bundle.putString(TrekAdmobDataKey.ICON_IMAGE, trekNativeAd.imgIcon.uri.toString())

        bundle.putString(TrekAdmobDataKey.ICON_IMAGE_HD, trekNativeAd.imgIconHd.uri.toString())

        if (trekNativeAd.mediaWidth > 0 && trekNativeAd.mediaHeight > 0) {

            bundle.putString(TrekAdmobDataKey.AD_SIZE_WIDTH, trekNativeAd.mediaWidth.toString())

            bundle.putString(TrekAdmobDataKey.AD_SIZE_HEIGHT, trekNativeAd.mediaHeight.toString())

        }

        extras = bundle

    }

    private fun setTrekImagesToAdmobImages(trekImages: List<ImgSrc>) {

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
                TrekAdmobNativeMappedImage(
                    img.image.drawable ?: BitmapDrawable(
                        context.resources, emptyBitmap
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

            //Cast only to ViewGroup: referencing com.google.android.gms.ads.nativead.NativeAdView
            //here would throw NoClassDefFoundError on GMA Next-Gen SDK hosts, where the
            //container is the next-gen NativeAdView (also a FrameLayout subclass)
            (containerView as? ViewGroup)?.let { nativeAdView ->

                //The asset maps are delivered synchronously in trackViews and tell us
                //whether the publisher's layout registered a MediaView asset, so this
                //is not a timing-dependent check. (ASSET_MEDIA_VIDEO is a compile-time
                //constant, so it gets inlined and no legacy class is referenced at
                //runtime on GMA Next-Gen SDK hosts — whose own ASSET_MEDIA_CONTENT
                //uses the same "3010" key.)
                val hasMediaAsset =
                    clickableAssetViews.containsKey(NativeAdAssetNames.ASSET_MEDIA_VIDEO) ||
                            nonClickableAssetViews.containsKey(NativeAdAssetNames.ASSET_MEDIA_VIDEO)

                //With a MediaView asset, measure the mapper's own TrekMediaView (handed
                //to setMediaView) directly: hosts may install it into the publisher's
                //MediaView only after trackViews returns, so an attach check here would
                //race and a tag search could match a publisher-owned view. Without one,
                //the TrekMediaView never gets attached — pass null so the tracker
                //measures the container view; binding the orphan view would mean
                //impressions and OM never fire.
                val mediaView: TrekMediaView? = if (hasMediaAsset) trekMediaView else null

                clickableAssetViews.values.forEach { view ->

                    trekNativeAd.setNativeAdClickAction(view)

                }

                TrekAdViewUtils.createViewStateTracker(trekNativeAd).apply {

                    nativeAdView.forEach { view->

                        this.addFriendlyObstruction(view)

                    }

                    this.launchViewStateTracker(nativeAdView, mediaView)

                }

            }

        }

    }

    override fun untrackView(view: View) {
        super.untrackView(view)

        trekNativeAd?.let {

            TrekAdViewUtils.destroyAd(it)

        }

    }

}