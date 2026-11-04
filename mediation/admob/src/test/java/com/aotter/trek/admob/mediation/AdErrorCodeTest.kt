package com.aotter.trek.admob.mediation

import com.aotter.net.trek.ads.TrekAdError
import com.google.android.gms.ads.AdRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class AdErrorCodeTest {
    @Test
    fun adErrorCodeOf_reportsTrekNoFillAsNoFill() {
        assertEquals(AdRequest.ERROR_CODE_NO_FILL, TrekAdmobDataKey.adErrorCodeOf(TrekAdError.NO_AD))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekAdmobDataKey.adErrorCodeOf(TrekAdError.UNKNOWN))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekAdmobDataKey.adErrorCodeOf(TrekAdError.NOT_INITIALIZED))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekAdmobDataKey.adErrorCodeOf(TrekAdError.AD_PLACE_REQUIRED))
    }
}
