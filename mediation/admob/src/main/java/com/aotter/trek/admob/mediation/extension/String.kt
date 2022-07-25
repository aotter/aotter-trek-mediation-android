package com.aotter.trek.admob.mediation.extension

import com.google.android.gms.ads.mediation.VersionInfo

fun String.getVersion(): VersionInfo {

    val versionNumber = this.split(".")

    if (versionNumber.count() >= 3) {

        val major = versionNumber[0].toInt()

        val minor = versionNumber[1].toInt()

        val micro = versionNumber[2].toInt()

        return VersionInfo(major, minor, micro)

    }

    return VersionInfo(0, 0, 0)

}