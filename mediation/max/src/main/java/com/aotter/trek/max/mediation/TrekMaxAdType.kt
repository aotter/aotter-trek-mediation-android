package com.aotter.trek.max.mediation

sealed class TrekMaxAdType(val value: String) {
    object NATIVE : TrekMaxAdType("NATIVE")
    object SUPR_AD : TrekMaxAdType("SUPR_AD")
    object BANNER : TrekMaxAdType("BANNER")
}