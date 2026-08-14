# Trek Mediation — Google Mobile Ads SDK 相容性

## 支援矩陣

| Trek mediation 版本 | Google Mobile Ads SDK (`play-services-ads`) | 說明 |
|---|---|---|
| **5.0.x**（如 `5.0.9`） | **21.x – 24.x** | 舊版。編譯於 21.3.0，使用已在 GMA **25.0 移除**的 `com.google.android.gms.ads.mediation.VersionInfo`，故**無法在 25.x 運作**。已發佈於 Artifactory、**不會移除**，供仍在舊 GMA 的 app 續用。 |
| **5.6.0** | **25.0 以上（至最新）** | 對齊現代 GMA。改用 `com.google.android.gms.ads.VersionInfo`。**要求 app `minSdkVersion` ≥ 23**（GMA 24 起的要求）。 |

**為何新舊不能並存於同一版**：GMA **25.0** 移除了 `mediation.VersionInfo`；`Adapter.getVersionInfo()` / `getSDKVersionInfo()` 的回傳型別是綁死的，一份 adapter build 只能站在此斷點的一邊。因此以 **GMA 25.0** 為分界：舊版對應 ≤24.x、新版對應 25.0+。

適用三個 adapter：`trek-admob-mediation`、`trek-gam-mediation`、`trek-max-mediation`（同版本線）。

## 升級到 GMA 25+ 版（給接入的開發者）

1. Google Mobile Ads SDK 升到 `com.google.android.gms:play-services-ads:25.x`（或更新）。
2. Trek mediation 依賴升到 GMA 25 支援版。
3. app 的 `minSdkVersion` 設為 **23 以上**。
4. `compileSdk` 建議 35 以上。

## 仍在舊 GMA（≤ 24.x）的 app

沿用現有的 `5.0.x` mediation（Artifactory 已發佈、不移除）；或先把 GMA 升到 25+，再改用新版。

> 註：`5.6.0` 另含媒體長寬比（aspectRatio）方向修正（第二項 BREAKING：升級後原生廣告 media view 的長寬比顯示方向會改變），詳見 release note；此與本頁 GMA 版本相容性屬不同議題，但同為本版變更。
