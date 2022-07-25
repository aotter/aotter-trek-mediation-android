package com.aotter.trek.gam.mediation

sealed class TrekGamAdType(val value: String) {
    object NATIVE : TrekGamAdType("NATIVE")
    object SUPR_AD : TrekGamAdType("SUPR_AD")
    object BANNER : TrekGamAdType("BANNER")
}