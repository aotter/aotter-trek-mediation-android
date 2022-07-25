package com.aotter.trek.admob.mediation

sealed class TrekAdmobAdType(val value: String) {
    object NATIVE : TrekAdmobAdType("NATIVE")
    object SUPR_AD : TrekAdmobAdType("SUPR_AD")
    object BANNER : TrekAdmobAdType("BANNER")
}