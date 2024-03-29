# Trek mediation

### Trek AdMob Mediation Document
https://trek.gitbook.io/aottertrek-sdk-doc/android/admob-mediation

### Trek AdMob Mediation release change log
- 2023/01/30 release - Mediation `4.8.3` (Recommend)
  - Add friendly obstrction method
  - Fix Mediation low in view rate  in OM SDK
  - Replace `TrekAdViewBinder` with `TrekAdViewUtils`
- 2022/10/03 release - Mediation `4.8.1`
  - Fix TrekMediaview play flow
  - New TrekNativeAdView
  - Upgrades ExoPlayer version to 2.18.1
  - New OnInitializationCompleteListener interface
  - Upgrades Kotlin version to 1.7.20
- 2022/10/03 release - Mediation `4.8.0`
  - Support new ad format of VAST XML & HTML 5
  - Log optimization
- 2022/08/04 release - Mediation `4.7.2`
  - TrekBannerAdView new feature
    - preload
    - auto refresh
  - TrekNativeAd object new feature
     - `images` object provider `drawable` 、`uri`
     -  remove unnecessary parameter
- 2022/06/22 release - Mediation `4.6.1`
     - New Sensor
     - Replace `TrekAd` with `TrekAdLoader`
     - The `TrekAdLoader.loadAds()` method sends a request for multiple ads (up to 5)
     - Replace `AdData` with `TrekNativeAd`
     - Replace `AotterService.initialize() method` with `TrekAds.initialize() method`
     - Replace `TrekBannerView` with `TrekBannerAdView`
     - New `TrekAdViewBinder` object (Using the object register ad layout)
     - Replace `JsonObject` with `TrekJsonObject`
     - Replace Gson with Kotlinx-serialization
     - Updating ExoPlayer version to `2.17.1`
     - Updating Kotlin version to `1.6.21`
- 2022/06/22 release - Mediation `4.5.0`
     - new TrekNativeAdImage 
     - Support vertical slide
     - Open browser with chrome
     - imp tool optimization
     - TrekMediaView optimization
     - Log optimization
- 2022/04/15 release - Mediation `4.4.5`
     - Optimized implementation
     - The TrekAdmobAdViewBinder class has been removed, and the admob mediation binding view flow has been integrated into the mediation adapter.
     - Update admob mediation custom adapter ( [Migrate to SDK v21](https://developers.google.com/admob/android/migration) )
- 2022/03/28 release - Mediation `4.4.2`
     - Add `setContentUrl()` & `setContentTitle()`
     - OMSDK obstruction minor adjustment
     - Add OMSDK contentUrl and customRefencData
     - Mapping Admob mediation `hasVideoContent` parameter
- 2022/03/22 release - Mediation `4.4.0`
     - change domain
     - support om json tag
     - support om js string dynamic update
     - support webview slide
     - improve BackgroundHolder setting
     - update ExoPlayer
- 2021/12/23 release - Mediation `4.3.4`
     - use Activity page when context comes from the application
- 2021/12/01 release - Mediation `4.3.2`
     - support android 12
     - support kotlin version 1.5.31
     - update exoplayer
     - adjust impression tool
     - adjust third click event flow
     - fix banner ad、supr.ad impression event
- 2021/09/27 release - Mediation `4.3.1`
     - Adjust om impressionType
- 2021/09/13 release - Mediation `4.3.0`
     - Optimize impression/VTR/CTR
     - Adjust TrekMediaView lifecycle
     - Adjsut TrekMediaView play video when visibility is 50% or more
     - New TrekBannerView
- 2021/08/31 release - Mediation `4.2.6`
     - remove unneeded permission 
- 2021/08/30 release - Mediation `4.2.5`
     - remove supr.ad third imp
- 2021/08/24 release - Mediation `4.2.4`
     - TrekMediaView default height
     - Defindes key of jsonObject
     - Add sdkVersion
     - Add mediationVersion
     - Optimization proguard
- 2021/07/29 release - Mediation `4.2.1`
    - Add isExpired method
    - Add isVideoAd method
- 2021/07/16 release - Mediation `4.2.0`
    - Optimize adData
    - Adjust TrekMediaView lifecycle
