package com.aotter.trek.admob.mediation.extension

import com.google.android.gms.ads.VersionInfo

fun String.getVersion(): VersionInfo {

    //MEDIATION_VERSION 形如 "AdMob_5.0.9"，先取最後一段底線後的版本再解析，避免 "AdMob_5".toInt() 拋 NumberFormatException
    val versionNumber = this.substringAfterLast('_').split(".")

    if (versionNumber.count() >= 3) {

        val major = versionNumber[0].toInt()

        val minor = versionNumber[1].toInt()

        val micro = versionNumber[2].toInt()

        return VersionInfo(major, minor, micro)

    }

    return VersionInfo(0, 0, 0)

}