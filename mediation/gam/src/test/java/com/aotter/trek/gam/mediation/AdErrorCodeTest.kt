package com.aotter.trek.gam.mediation

import com.aotter.net.trek.ads.TrekAdError
import com.google.android.gms.ads.AdRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class AdErrorCodeTest {
    @Test
    fun adErrorCodeOf_reportsTrekNoFillAsNoFill() {
        assertEquals(AdRequest.ERROR_CODE_NO_FILL, TrekGamDataKey.adErrorCodeOf(TrekAdError.NO_AD))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekGamDataKey.adErrorCodeOf(TrekAdError.UNKNOWN))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekGamDataKey.adErrorCodeOf(TrekAdError.NOT_INITIALIZED))
        assertEquals(AdRequest.ERROR_CODE_INTERNAL_ERROR, TrekGamDataKey.adErrorCodeOf(TrekAdError.AD_PLACE_REQUIRED))
    }
}
