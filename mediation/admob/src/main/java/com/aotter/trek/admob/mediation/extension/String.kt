package com.aotter.trek.admob.mediation.extension

import com.google.android.gms.ads.VersionInfo

fun String.getVersion(): VersionInfo {

    //MEDIATION_VERSION looks like "AdMob_5.0.9": parse the part after the last underscore,
    //otherwise "AdMob_5".toInt() throws NumberFormatException
    val versionNumber = this.substringAfterLast('_').split(".")

    if (versionNumber.count() >= 3) {

        val major = versionNumber[0].toInt()

        val minor = versionNumber[1].toInt()

        val micro = versionNumber[2].toInt()

        return VersionInfo(major, minor, micro)

    }

    return VersionInfo(0, 0, 0)

}