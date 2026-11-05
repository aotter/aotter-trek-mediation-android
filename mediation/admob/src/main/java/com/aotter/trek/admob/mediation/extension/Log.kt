package com.aotter.trek.admob.mediation.extension

import android.util.Log
import com.aotter.net.trek.TrekAds
import com.aotter.net.trek.TrekLogLevel

// Informational logs follow the level the app sets with TrekAds.setLogLevel (NONE by default).
// Errors keep going straight to Log.e.
internal fun logAt(level: TrekLogLevel, tag: String, message: String) {
    if (TrekAds.getLogLevel() >= level) Log.i(tag, message)
}
